package com.study.domain.post;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.domain.login.UsersService;
import com.study.paging.PagingResponse;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;


    @Autowired
    private final UsersService usersService;

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }


    // 게시글 작성 페이지
    // RequestParam은 get방식 쿼리스트링 받을 때 주로 사용!!!
    @GetMapping("/post/write.do")
    public String openPostWrite(@RequestParam(value = "id", required = false) final Integer id, Model model) {
        //System.out.println("id =================> " + id);
        if (id != null) {
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);

            // 댓글
            List<AttachDto> fileList = postService.getAttachFileList(id);
            System.out.println("fileList ===================> " + fileList);
            model.addAttribute("fileList", fileList);
        
        }

        return "post/write";
    }

    // 신규 게시글 생성
    @PostMapping("/post/save.do")
    //public String savePost(final PostRequest params, Model model, @RequestParam("files") MultipartFile[] files) { 
    public String savePost(final PostRequest params, Model model, MultipartFile[] files) { // @RequestPart(value = "files", required = false) 사용안해도 이미지 등록이됨 이유 : 이름이 같으면 자동 매핑 처리
        System.out.println("filesfilesfilesfilesfilesfiles=========> " + files);
        System.out.println("filesfilesfilesfilesfilesfiles=========> " + params);

        //System.out.println(file1);
        //System.out.println(file2);

        /*  MultipartHttpServletRequest reuqest로 파일 여러개 받았을 때
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if (iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }    
        System.out.println(file);
        */

        postService.savePost(params, files);
        MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // 게시글 리스트 페이지
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") SearchDto params, Model model) {

        System.out.println("params =================================================================> " + params);
        PagingResponse<PostResponse> response = postService.findAllPost(params);

        model.addAttribute("response", response);
        return "post/list";
    }

    // 게시글 checkbox 삭제
    // ajax호출 시 @Controller 일경우 @ResponseBody 작성해줘야함
    @PostMapping("/post/deleteChk.do")
    @ResponseBody 
    public int deletePostList(@RequestParam int[] paramSeqs) throws Exception{
        return postService.delete(paramSeqs);
    }

    // 게시글 상세 페이지
    @GetMapping("/post/view.do")
    public String openPostView(@ModelAttribute("params") CommentDto params, @RequestParam final int id, Model model) {
    //public String openPostView(@ModelAttribute("params") CommentDto params, @RequestParam(value = "id", required = false) String id, Model model) { // url파라미터 값 String으로 받아도 문제없음 findPostById(id) 넘기기전에 Integer.parse(id)만 적용해주면 됨

        System.out.println("view 파라미터 확인 ========> " + params); // Get -> queryString 파라미터 확인 

        PostResponse post = postService.findPostById(id);
        model.addAttribute ("post", post); // 게시판 상세 내용

        List<AttachDto> fileList = postService.getAttachFileList(id); // 파일 상세
        model.addAttribute("fileList", fileList);
        
        return "post/view";
    } 

    /*  
    // 기존 게시글 수정 from action으로 처리했을 때 사용 컨트롤러 (file 업로드 하기 전)
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params, Model model) {
        postService.updatePost(params);
        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
    */

    /*  
    // 게시글 수정 ajax 사용해보기 (file 업로드 하기 전 ajax 통신!!! )
    // ajax로 넘겨주는 data가 json형식이기 때문에 @RequestBody로 꼭 받아줘야한다!!
    // ajax호출 컨트롤러이기 때문에 @ResponseBody를 꼭 작성해줘야한다!! 
    @PostMapping("/post/update.do")
    @ResponseBody
    public int updatePost(@RequestBody PostRequest params, Model model) { 
        int result = postService.updatePost(params);
        return result;
    }
    */


    // ajax file 업로드 ajax 통신!!! (multipartFile) 
    @PostMapping("/post/update.do")
    @ResponseBody
    //public int updatePost(@ModelAttribute PostRequest params, Model model, MultipartFile[] file) {   
    public boolean updatePost(@ModelAttribute PostRequest params, Model model, @RequestPart(value = "files", required = false) MultipartFile[] file) {  
                                                                                                     // MultipartFile[] files의  ---> files는 input type="file"의 name으로 작성해줘야한다!!!
        System.out.println("params =================================================================> " + params); // write.html 에서 hidden 값으로 넘어온 것들, form태그들 확인하기!!
        //System.out.println("params =================================================================> " + files);   
        System.out.println("MultipartFile[] =================================================================> " + file);   

        //int result = 0;
        boolean result = postService.updatePost(params, file);
        return result;
    }



    /*  
    // 게시글 삭제 list.html 페이징 처리 전 컨트롤러
    // 게시글 삭제 form submit이기에 id하나만 필요하더라도 Command객체로 받기 가능하다!! check!!
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam int id, Model model) {
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
    */

    // view.html 게시글 삭제
    // form submit으로 값들을 컨트롤러로 보내서 @RequestParam으로 받음
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam Map<String,Object> queryParams, Model model) {

        System.out.println("queryParams =======================> " + queryParams);
        Map<String,Object> queryParams2 = postService.deletePost(queryParams);

        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParams2);
        return showMessageAndRedirect(message, model);
    }

    // 상세화면 파일 다운로드
	@GetMapping("/post/download.do")
	public void downloadAttachFile(@RequestParam(value = "idx", required = false) final Integer idx, Model model, HttpServletResponse response) throws ParseException {

		if (idx == null) 
            throw new RuntimeException("올바르지 않은 접근입니다.");

		AttachDto fileInfo = postService.getAttachDetail(idx);
		if (fileInfo == null || "Y".equals(fileInfo.getDeleteYn())) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}

        // VO에서 fileInfo.getInsertTime() 값
        String temp = fileInfo.getInsertTime();

        // 문자열 -> date (insertTime이 String이기 때문에 format형식 바꿔주려면 date형태로 변환)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(temp);
      	
		// date -> 문자열 (format형식 사용)
        SimpleDateFormat dateLocal = new SimpleDateFormat("yyMMdd");
        String uploadDate = dateLocal.format(date);

		String uploadPath = Paths.get("C:", "dev_workspace", "upload", uploadDate).toString();

		String filename = fileInfo.getOriginalName();

        // file객체 생성 (path에 있는 파일정보)
		File file = new File(uploadPath, fileInfo.getSaveName());
        System.out.println(file);
        System.out.println(file.getName());
        System.out.println(file.getPath());


        
		try {

            // new File()을 이용해 path에 있는 파일 정보를 가져온다.
            // byte[] data = FileUtils.readFileToByteArray(new File(path)); // 정보의 저장위치(storedFilPath)를 이용해 파일을 읽은후 byte[]형태로 변환, 
			byte[] data = FileUtils.readFileToByteArray(file);  // 파일을 Byte배열로 변환한다. 

            //response헤더에 타입, 크기,형태를 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";"); 

            //읽어온 파일 정보를 response에 작성
			response.getOutputStream().write(data);
            //버퍼정리후 닫아줌
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}

	}



}