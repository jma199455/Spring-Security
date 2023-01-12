package com.study.domain.login;

import lombok.Getter;

public enum UserAuthority implements ConstImpl{
  
    ADMIN("관리자"), USER("유저");

	@Getter
	private String label;

	private UserAuthority(String label) {
		this.label = label;
	}

	public String getKey() {
		return name();
	}
    
}
