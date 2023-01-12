package com.study.domain.post;

import java.time.LocalDateTime;

import com.study.paging.PaginationComment;

import lombok.Data;

@Data
public class CommentDto {

	private Integer idx;

	private int boardIdx;

	private String content;

	private String writer;

	/** 삭제 여부 */
	

	private String deleteYn;
	
	/** 등록일 */
	private String insertTime;

	/** 수정일 */
	private String updateTime;
	
	/** 삭제일 */
	private String deleteTime;


	private int id;				  // 파리미터 id 값 받는 변수 
	private int page;             // 현재 페이지 번호
    private int recordSize;       // 페이지당 출력할 데이터 개수
    private int pageSize;         // 화면 하단에 출력할 페이지 사이즈

	
    /** 페이징 정보 */
	private PaginationComment pagination;


	public CommentDto(){
        this.page = 1;
        this.recordSize = 10;
        this.pageSize = 10;
	}


}
