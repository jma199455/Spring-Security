package com.study.domain.post;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class PostResponse {

    private int id;                       // PK
    private String title;                  // 제목
    private String content;                // 내용
    private String writer;                 // 작성자
    private int viewCnt;                   // 조회 수
    private Boolean noticeYn;              // 공지글 여부
    
    //private Boolean deleteYn;              // 삭제 여부
	
	private String createdDate;     // 생성일시
    private String modifiedDate;    // 최종 수정일시

    private int rm; // 번호순번

	//private LocalDateTime createdDate;     // 생성일시
    //private LocalDateTime modifiedDate;    // 최종 수정일시

	//private Date createdDate;     // 생성일시
    //private Date modifiedDate;    // 최종 수정일시
    
    
    private Long idx;

	/** 게시글 번호 (FK) */
	private int boardIdx;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String saveName;

	/** 파일 크기 */
	private long size;

    private String deleteYn;
	
	/** 등록일 */
	private String insertTime;

		/** 삭제일 */
    private String deleteTime;
    
    
}
