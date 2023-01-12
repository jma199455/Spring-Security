package com.study.domain.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data

public class UserDetailsImpl implements UserDetails{

    /*  
    private static final long serialVersionUID = 1L;

	private String username; // 로그인 아이디(loginId)
	private String password;

	private int adminSeq;
	private Integer partnerSeq;
	private String name;
	private String email;
	private UserAuthority authority;
	private boolean isMaster;

    
    //private GrantedAuthority authorities; // 제거
    private String id;          // 제거
    private String pw;          // 제거


	// 파트너 정보
	// private String partnerName;
	// private boolean isApp;
	// private Integer appSeq;

	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(AdminVO admin) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(admin.getAuthority().getKey()));

		this.username = admin.getLoginId();
		this.password = admin.getPassword();

		this.adminSeq = admin.getAdminSeq();
		this.partnerSeq = admin.getPartnerSeq();
		this.name = admin.getAdminName();
		this.email = admin.getEmail();
		this.authority = admin.getAuthority();
		this.isMaster = admin.isMaster();

		// this.partnerName = admin.getPartnerName();
		// this.isApp = admin.isApp();

		this.authorities = authorities;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    */
}
