
package com.study.domain.login;

import lombok.Data;

@Data
public class UsersDto {
    /*  
    private String id;  
    private String pw;  
    private String userName;
    */

    private int userSeq;
    private String id;  
    private String pw;  
    private String userName;
    private String createDate;
    private String loginDtm;

    private String genderCode;
    private String email;




    /*  
    // 권한
    private UserAuthority authority;
    private String username; // 로그인 아이디(id)
	private String password; // 로그인 페스워드 (pw)
    */

    
}
