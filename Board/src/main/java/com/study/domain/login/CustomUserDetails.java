package com.study.domain.login;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L; 
	
	private String username; // 유저 ID // 혹은 유저 이름
	private String password; // 유저 PW
 
	private boolean isEnabled; // 계정이 활성화 되었는지
	private boolean isAccountNonExpired; // 유저 아이디가 만료 되었는지
	private boolean isAccountNonLocked;	 // 유저 아이디가 Lock 걸렸는지
	private boolean isCredentialsNonExpired; // 비밀번호가 만료 되었는지
	//private Collection<? extends GrantedAuthority>authorities;	//유저가 갖고 있는 권한 목록
	private List<GrantedAuthority> authorities; //유저가 갖고 있는 권한 목록



	////////////////////////////// LoginUtils.java SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 부분 null값 나와서 사용 못하고 있음
	private UserAuthority authority; // 권한 정보 가져오기
	// setter
	public void setUserAuthority(UserAuthority authority) {
		this.authority = authority;
	}

	// getter 
	public UserAuthority getUserAuthority() {
		return authority;
	}
	//////////////////////////////// 


	// 1. 오버라이드 방법
	// loadUserByUsername 메소드에서 setter 후 CustomUserDetails를 return 할 경우 isEnabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired setter 생성을 해준다.
	// username, password, authorities 는 setter가 필수!
	@Override
	public String getUsername() { 
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}
	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	/*   
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	// 이 메소드를 통해 한 계정에 권한을 몇개 가졌는지 확인이 가능
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	*/

	@Override
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<GrantedAuthority> authorities) {	// CustomUserDetailService.java 에서 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담고 있음 
		this.authorities = authorities;
	}


	// 2. 오버라이드 방법
	// loadUserByUsername 메소드에서 CustomUserDetails 필드를 setter를 사용하지 않고 return 할 경우 isEnabled, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired setter를 생성을 하지 않고 getter return 값에 true를 넣어준다
	// username, password, authorities 는 setter가 필수!

	/*  
	private String username;
	private String password;
	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private Collection<? extends GrantedAuthority>authorities;

	
	@Override
	// ID
	public String getUsername() {

		return username;
	}

	// setter
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	// PW
	public String getPassword() {

		return password;
	}

	// setter
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	// 권한
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	// setter (컨트롤러에서 처리 안하고 여기서 처리할 때)
	public void setAuthorities(List<String> authList) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (int i = 0; i < authList.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(authList.get(i)));
		}

		this.authorities = authorities;
	}



	@Override
	// 계정이 만료 되지 않았는가?
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	// 계정이 잠기지 않았는가?
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	// 패스워드가 만료되지 않았는가?
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	// 계정이 활성화 되었는가?
	public boolean isEnabled() {

		return true;
	}

	*/

}
