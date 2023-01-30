package com.study.domain.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

    /*  
    // Gson 사용
    // (0. new Gson().toJsonTree(commentList) 사용하기)
    @GetMapping(value = "/comments/{boardIdx}")
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) {

		JsonObject jsonObj = new JsonObject();

		List<CommentDto> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {

			JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
            
            //System.out.println("jsonArr ==========> " + jsonArr);

			jsonObj.add("commentList", jsonArr);
		}
        
		return jsonObj;
	}
    */
    
    /*  
    // Jackson 사용
    // List<CommentDto> JSONObject 리턴하기
	@GetMapping(value = "/comments/{boardIdx}")
	public JSONObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException {
        
		List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray();
        
        HashMap<String,Object> contents = null;
        CommentDto noticeInfo = new CommentDto();
        List<CommentDto> list = new ArrayList<CommentDto>();

        int listSize = commentList.size();

        // 이게 깔끔
        for (CommentDto noticeInfo2 : commentList) {

            CommentDto dto = new CommentDto();
            dto.setIdx(noticeInfo2.getIdx());
            dto.setBoardIdx(noticeInfo2.getBoardIdx());
            dto.setContent(noticeInfo2.getContent());
            dto.setWriter(noticeInfo2.getWriter());
            dto.setDeleteYn(noticeInfo2.getDeleteYn());
            dto.setInsertTime(noticeInfo2.getInsertTime());
            list.add(dto);

        }

        //String jsonInString = mapper.writeValueAsString(list);  // Json 문자열로 변환!!!
        //System.out.println(jsonInString);
        // JsonParser jsonParser = new JsonParser(); 사용할 수 가 없음.

        JSONObject jsonObj = new JSONObject();
        // jsonObj.put("commentList", jsonInString); Json 문자열이 리턴되고 있음.. 
        jsonObj.put("commentList", list); // list를 JSONObject에 넣어주면 정상적으로 데이터 확인이 되고 있음 , jackson은 따로 writeValueAsString와 JsonParser를 안해줘도 된다.
        
        return jsonObj;

	}
    */


    /*  
    // Jackson 사용
    // (1. HashMap<String,Object> JSONObject 리턴하기 )
    @GetMapping(value = "/comments/{boardIdx}")
    public JSONObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException {
        
        List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray();


        CommentDto noticeInfo = null;
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        
        int listSize = commentList.size();
        for (int i=0; i < listSize; i++) {
            
            noticeInfo = commentList.get(i);

            HashMap<String,Object> contents = new HashMap<>();
            contents.put("idx", noticeInfo.getIdx());
            contents.put("boardIdx", noticeInfo.getBoardIdx());
            contents.put("content", noticeInfo.getContent());
            contents.put("writer", noticeInfo.getWriter());
            contents.put("deleteYn", noticeInfo.getDeleteYn());
            contents.put("insertTime", noticeInfo.getInsertTime());

            list.add(contents);
            
        }
        // String jsonInString = mapper.writeValueAsString(list);  // Json 문자열로 변환
        // JsonParser jsonParser = new JsonParser(); 사용할 수 가 없음.

        JSONObject jsonObj = new JSONObject();
        // jsonObj.put("commentList", jsonInString); Json 문자열이 리턴되고 있음.. 
        jsonObj.put("commentList", list);
        
        return jsonObj;

    }
    */
   

    /*  
    // Gson 라이브러리 사용 
    // (1. HashMap<String,Object> JsonObject로 리턴하기 )
	@GetMapping(value = "/comments/{boardIdx}")
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException, ParseException {

		List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper(); // writeValueAsString 함수 사용
        //List<CommentDto> list = new ArrayList<>(); 

        int listSize = commentList.size();

        //HashMap<String,Object> contents = null; 메모리 낭비
        JsonArray jsonArr = new JsonArray(); // JsonArray 선언 (for문 밖에서 return 해주기 위해서)

        HashMap<String,Object> contents = new HashMap<>();

        for (int i=0; i < listSize; i++) {
            CommentDto noticeInfo = new CommentDto(); // for문 밖에 선언해도 상관없음
            noticeInfo = commentList.get(i);

            //contents = new HashMap<>(); 메모리 낭비
            // 각 필드 map에 mapping
            contents.put("idx", noticeInfo.getIdx());
            contents.put("boardIdx", noticeInfo.getBoardIdx());
            contents.put("content", noticeInfo.getContent());
            contents.put("writer", noticeInfo.getWriter());
            contents.put("deleteYn", noticeInfo.getDeleteYn());
            contents.put("insertTime", noticeInfo.getInsertTime());
            
            String temp = mapper.writeValueAsString(contents);  // Json 문자열로 변환
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(temp);   // JsonParser.parse를 이용하여 문자열을 JsonElement로 변환
            jsonArr.add(jsonElement);   // jsonArr.add(JsonElement or JsonObject가 들어간다!!);
            //jsonArr.add(jsonParser.parse(temp));   // map이여서 따로 jsonElement로 변환 하지 않아도 된다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 결과 같음!!!



        }

        JsonObject jsonObj = new JsonObject(); // 마지막 return 핧 jsonObject 객체 선언
        jsonObj.add("commentList", jsonArr); // 객체에 jsonArr 넣기

        return jsonObj; // return
	}
    */                

    // Gson 라이브러리 사용 
    // (1. List<CommentDto> JsonObject로 리턴하기 )
    @GetMapping(value = "/comments/{boardIdx}")                              // 파라미터로 요청값을 받음
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute CommentDto params) throws JsonProcessingException, JSONException {
                                     // 여기 지우고 params 확인해보기 -> 정상작동
    /*
    @RequestParam 로 각자 받을 수도 있음
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx
                                                                        , @RequestParam(value = "page", required = false, defaultValue = "1") int page
                                                                        , @RequestParam(value = "recordSize", required = false, defaultValue = "10") int recordSize
                                                                        , @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize ) throws JsonProcessingException, JSONException {
                                                                        
    */
        /*
        @RequestParam 로 각자 받고 따로 매핑 해줘야함  
        CommentDto params = new CommentDto();
        params.setPage(page);
        params.setRecordSize(recordSize);
        params.setPageSize(pageSize);
        params.setBoardIdx(boardIdx);
        */

        System.out.println("boardIdx ===========================> " + boardIdx);
        System.out.println("params확인ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ : " + params);

        // 리스트 출력
        List<CommentDto> commentList = commentService.getCommentList(params);
        System.out.println(commentList);

        // 댓글 페이징 그리기 처리 PaginationComment 클래스
        CommentDto pageDto = commentService.getcommentlListPage(params);
        
        // System.out.println("pageDto1111111 =====================================================> " + pageDto);                     /// CommentDto 전체 확인
        // System.out.println("pageDto2222222 =====================================================> " + pageDto.getPagination());     /// Pagination 부분 확인


        ObjectMapper mapper = new ObjectMapper(); // writeValueAsString 함수 사용
        List<CommentDto> list = new ArrayList<>(); 

        int listSize = commentList.size();
        
        //CommentDto dto = new CommentDto(); // 여기다 선언하면 리스트에 데이터 하나가 반복되서 들어감 확인!!!!!!!!! 
        JsonElement jsonElement = null;      // for문 밖에서 jsonObj.add 에 넣어주기 위해서

        JsonElement jsonElement2 = null;     // Pagination 값들 넣기

        JsonElement jsonElement3 = null;     // page값들 확인 후 삭제


        for (int i=0; i < listSize; i++) {
            CommentDto noticeInfo = new CommentDto(); // for문 밖에 선언해도 상관없음 아래처럼.
                                                      // Ex) CommentDto noticeInfo = null
                                                      //     noticeInfo = new CommentDto();
            noticeInfo = commentList.get(i);

            CommentDto dto = new CommentDto();

            dto.setIdx(noticeInfo.getIdx());
            dto.setBoardIdx(noticeInfo.getBoardIdx());
            dto.setContent(noticeInfo.getContent());
            dto.setWriter(noticeInfo.getWriter());
            dto.setDeleteYn(noticeInfo.getDeleteYn());
            dto.setInsertTime(noticeInfo.getInsertTime());
            // commentList에 getPagination 넣을 경우 사용 dto.setPagination(noticeInfo.getPagination());

            list.add(dto);  // List<CommentDto> 에 넣기
        }
  
        System.out.println(list);
        String temp = mapper.writeValueAsString(list);  // Json 문자열로 변환
        System.out.println(temp);

        JsonParser jsonParser = new JsonParser();
        jsonElement = jsonParser.parse(temp);   // JsonParser.parse를 이용하여 문자열을 JsonElement로 변환
        System.out.println(jsonElement);

        JsonObject jsonObj = new JsonObject(); // 마지막 return 할 jsonObject 객체 선언
        jsonObj.add("commentList", jsonElement); // 리스트 jsonElement 

        String temp2 = mapper.writeValueAsString(pageDto.getPagination());  // Json 문자열로 변환
        JsonParser jsonParser2 = new JsonParser();
        jsonElement2 = jsonParser2.parse(temp2);   // JsonParser.parse를 이용하여 문자열을 JsonElement로 변환

        jsonObj.add("PaginationComment", jsonElement2); // 페이징 jsonElement 

        // 쿼리스트링 page 값 set 설정
        String temp3 = mapper.writeValueAsString(pageDto);  // Json 문자열로 변환 
        JsonParser jsonParser3 = new JsonParser();
        jsonElement3 = jsonParser3.parse(temp3);   // JsonParser.parse를 이용하여 문자열을 JsonElement로 변환

        jsonObj.add("pageAttr", jsonElement3);

        return jsonObj; // return
}









    // 등록 , 수정
    // @PostMapping(value = { "/comments", "/comments/{idx}" }) 이거도 가능 !!
	@RequestMapping(value = { "/comments", "/comments/{idx}" }, method = { RequestMethod.POST, RequestMethod.PATCH })
	public JsonObject registerComment(@PathVariable(value = "idx", required = false) Integer idx, @RequestBody final CommentDto params) {

		JsonObject jsonObj = new JsonObject();

		try {
            /*  
			if (idx != null) {
				params.setIdx(idx);
			}
            */

			boolean isRegistered = commentService.registerComment(params);
			jsonObj.addProperty("result", isRegistered);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}

    
    // 삭제
    @DeleteMapping(value = "/comments/{idx}")
	public JsonObject deleteComment(@PathVariable("idx") final int idx) {

		JsonObject jsonObj = new JsonObject();

		try {
			boolean isDeleted = commentService.deleteComment(idx);
			jsonObj.addProperty("result", isDeleted);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}


}
