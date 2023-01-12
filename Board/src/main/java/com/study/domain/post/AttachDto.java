package com.study.domain.post;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AttachDto {
   
    /** 파일 번호 (PK) */
	private Long idx;

	/** 게시글 번호 (FK) */
	private int boardIdx;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String saveName;

	/** 파일 크기 */
	private long size;

	/* 파일삭제 여부 */
    private String deleteYn;
	
	/** 등록일 */
	private String insertTime;

	/** 삭제일 */
    private String deleteTime;



}
