package com.study.domain.login;

import lombok.Data;

@Data
public class AdminVO {
    
    private Integer adminSeq;
	private Integer partnerSeq;


	private String loginId; // 로그인 아이디
	private String password; // 비밀번호

	private String adminName;
	private String email;
	private String department;
	private String tel;
	private String description;
	private UserAuthority authority;
	private boolean isMaster;

}
