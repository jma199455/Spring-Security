package com.study.domain.login;

import lombok.Data;

@Data
public class UserEntity {
    
    //private String loginUserId; 
    //private String password;

    // DB에서 가져올 결과 VO 들
    private int userSeq; // 시퀀스 
    private String id;  // 아이디
    private String pw;  // 비밀번호

    //private String userName;   // 사용자이름 
    //private String createDate; // 회원가입일
    //private String genderCode; // 성별코드 


}
