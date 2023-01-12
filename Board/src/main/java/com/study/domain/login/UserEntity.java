package com.study.domain.login;

import lombok.Data;

@Data
public class UserEntity {
    
    private String loginUserId;
    private String password;


    private int userSeq; // 시퀀스 
    private String id;  // 아이디
    private String pw;  // 비밀번호

}
