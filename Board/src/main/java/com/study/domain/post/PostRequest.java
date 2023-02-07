package com.study.domain.post;

import java.util.List;

import lombok.Data;

@Data
public class PostRequest {


	private int id;             // PK
    private String title;        // 제목
    private String content;      // 내용
    private String writer;       // 작성자
    private Boolean noticeYn;    // 공지글 여부 , 현재 사용안함 write.html에 hidden값으로 작성되있음  사용이유 모르겠음. 


	/** 비밀 여부 */
	private String secretYn;

	/** 파일 변경 여부 */
	private String changeYn;

    /* 파일 등록 여부 추가 */
    //private String addCk;

	/** 파일 인덱스 리스트 */
	private List<Integer> fileIdxs;
	
	//private List<String> files;
	//private int[] fileIdxs; 파일 인덱스 리스트 배열로 받아볼 때 사용


	
}
