package com.study.domain.post;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.study.common.dto.SearchDto;

@Mapper
public interface PostMapper {

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     */
    public int save(PostRequest params);


    // public int save(PostRequest params, MultipartFile[] files);


    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostResponse findById(int id);
    
    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    public int update(PostRequest params);

    /**
     * 게시글 삭제
     * @param id - PK
     */
    public int deleteById(@Param("map") Map<String,Object> map);
                        
    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public List<PostResponse> findAll(SearchDto params);

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    public int count(SearchDto params);
    
    // 리스트 선택삭제 
    public int delete(int id);

    // 조회수 업데이트
    public Integer updateCnt(int id);

}
