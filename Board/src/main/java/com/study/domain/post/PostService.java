package com.study.domain.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.study.common.dto.SearchDto;
import com.study.common.util.FileUtils;
import com.study.paging.Pagination;
import com.study.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostMapper postMapper;

    @Autowired
	private AttachMapper attachMapper;

    @Autowired
	private FileUtils fileUtils;
    
    /**
     * 게시글 저장
     * @param params - 게시글 정보
     * @return Generated PK
     */
    /*  
    @Transactional
    public boolean savePost(final PostRequest params) {
        int queryResult = 0;

        queryResult = postMapper.save(params);
        return (queryResult == 1) ? true : false;
    }
    */

    @Transactional
    public boolean savePost(final PostRequest params, MultipartFile[] files) {
		int queryResult = 0;

        queryResult = postMapper.save(params); // 마이바티스의 useGeneratedKeys="true" keyProperty="id" 속성을 추가해 INSERT 쿼리의 실행과 동시에 생성된 PK가 파라미터로 전달된 객체 즉  PostRequest 클래의 객체에 담기게 된다.
        
		List<AttachDto> fileList = fileUtils.uploadFiles(files, params.getId());

		if (CollectionUtils.isEmpty(fileList) == false) {
			queryResult = attachMapper.insertAttach(fileList);
			if (queryResult < 1) {
				queryResult = 0;
			}
		}

		return (queryResult > 0);

    }


    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostResponse findPostById(int id) {
        postMapper.updateCnt(id);

        PostResponse result = postMapper.findById(id);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        
        try {
            Date date = formater.parse(result.getCreatedDate());
            result.setCreatedDate(formater.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    // 파일 정보 가져오기 write.html
    public List<AttachDto> getAttachFileList(int id) {

        int fileTotalCount = attachMapper.selectAttachTotalCount(id);

        System.out.println("fileTotalCount ===================> " + fileTotalCount);

        if (fileTotalCount < 1) {
            return Collections.emptyList(); // 없을 경우
        }
        return attachMapper.selectAttachList(id);
    }







    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public boolean updatePost(final PostRequest params, MultipartFile[] files) {

        System.out.println("getFileIdxsgetFileIdxsgetFileIdxsgetFileIdxsgetFileIdxs ====> " + params.getFileIdxs());

        int queryResult = 0;

        queryResult = postMapper.update(params);

        // 파일 추가, 삭제, 그냥변경된 경우
		if ("Y".equals(params.getChangeYn())) {

			attachMapper.deleteAttach(params.getId()); // 기존 등록된 이미지들 전체 일단 삭제여부 Y로 전부
            System.out.println(params.getFileIdxs());   // 파일이 변경되면 FileIdxs hidden값 제거
            
			if (CollectionUtils.isEmpty(params.getFileIdxs()) == false) {   // 변경하지 않은 파일 FileIdxs를 다시 삭제여부 N으로 업데이트
				int temp = attachMapper.undeleteAttach(params.getFileIdxs());
                System.out.println(temp);
			} 

            // 새로 등록한 files가 있는 경우 파일 테이블에 등록.
            if (files != null && !files.equals("")){
                // 1. 기존 등록된 이미지 다 지우고
                // 2. 남아있는 idx들 N로 업데이트
                // 3. 추가된 idx들 insert 해주기
                List<AttachDto> fileList = fileUtils.uploadFiles(files, params.getId());
                if (CollectionUtils.isEmpty(fileList) == false) {
                    queryResult += attachMapper.insertAttach(fileList);
                    if (queryResult < 1) {
                        queryResult = 0;
                    }
                } 
            }
        }

        return (queryResult > 0);
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    public Map<String,Object> deletePost(Map<String,Object> queryParams) {
        
        System.out.println("queryParams ===============>" + queryParams);
        postMapper.deleteById(queryParams);
        queryParams.remove("id");
        return queryParams;
    }

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트 , pagination 필드값들
     */
    public PagingResponse<PostResponse> findAllPost(SearchDto params) {

        // 게시글 카운트
        int count = postMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }       

        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);
        
        // System.out.println("여기는 Pagination 확인" + params); 
        List<PostResponse> list = postMapper.findAll(params);

        // String 타입 createDate 따로 처리해줄 때 사용 (리스트 등록날짜 화면에 사용) 
        /*  
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd");
        for (PostResponse postResponse : list) {
            try {
                // 문자열을 Date 객체로 파싱
                Date date = formater.parse(postResponse.getCreatedDate());
                //System.out.println(date);
                //System.out.println(formater.format(date));
                postResponse.setCreatedDate(formater.format(date));
                //postResponse.setCreatedDate(formater.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        */


        // 1. 
        /*  
        // return new PagingResponse<>(list, pagination); 생성자로 return하지 않고 set으로 넣어보기 ==> 가능
        PagingResponse<PostResponse> temp = new PagingResponse<>();
        temp.setList(list);
        temp.setPagination(pagination);
        return temp;
        */

        // 2. 
        return new PagingResponse<>(list, pagination);
    }

    public int delete(int[] ids) {

        int result = 0;
        for (int id : ids) {
            System.out.println(id);
            result += postMapper.delete(id);
        }
        return result;

    }


    // 파일 다운로드
    public AttachDto getAttachDetail(int idx) {
        return attachMapper.selectAttachDetail(idx);
    } 

}