package com.study.domain.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.common.util.LoginUtils;

@Service
public class CustomUserDetailService implements UserDetailsService{


    //데이터베이스에서 로그인 아이디에 해당하는 정보를 읽어 들이기 위해서
    //UserDbService를 구현한 객체를 주입받고 있다.
    @Autowired
    UsersMapper usersMapper;

    //@Autowired
    //private final PasswordEncoder passwordEncoder;

    /* DB에서 유저정보를 불러온다.
	 * Custom한 Userdetails 클래스를 리턴 해주면 된다.
	 * */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        UserEntity customUser = usersMapper.getUser(loginId);
        System.out.println(customUser);
        System.out.println("customUser ==================> " + customUser);

		if(customUser==null) {
			throw new UsernameNotFoundException("사용자가 입력한 아이디에 해당하는 사용자를 찾을 수 없습니다.");    // 해당 메세지가 안뜸, 이유 모르겠음..
        }

        CustomUserDetails customUserDetails = new CustomUserDetails();
        //customUserDetails.setUsername(customUser.getLoginUserId());
        //customUserDetails.setPassword(customUser.getPassword());


        // check!!! 비밀번호 암호화 설정 꼭 해야 인증이 넘어감!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
        // 주의 하실 점은 User객체를 만드는 코드에 PasswordEncoder를 사용하여 DB에 저장된 원래 패스워드를 암호화하도록 코드가 짜여져 있는데요. 원래는 DB에 저장된 값 자체가 암호화 되어있어야합니다. 즉. 이미 넣을때 passwordEncoder를 통해 암호화된 문자열이 들어가있어야 하는데 편의상 저는 User객체를 리턴해줄때 암호화를 하였습니다.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);  
        // 회원가입 암호화 전 String result = encoder.encode(customUser.getPw()); // 서버 내에서 인코딩해주기 때문에 로딩 시간 오래걸리고 있는 것 같음 , insert할때 암호화 하고 해당 코드 부분 주석처리 해        System.out.println(result);
        // check!!! 비밀번호 암호화 설정 꼭 해야 인증이 넘어감!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        customUserDetails.setUsername(customUser.getId());
        customUserDetails.setPassword(customUser.getPw()); // 그냥 암호화 없이 UserDetails 넘기면 로그인 인증되지 않음!!!!
        // 회원가입 암호화 전 customUserDetails.setPassword(result); // User객체를 리턴해줄때 암호화. 

        // 사용자 권한 정보
        List<UserRoleEntity> customRoles = usersMapper.getUserRoles(customUser.getUserSeq());
        System.out.println("customRoles ====> " + customRoles);


        /* LoginUtils클래스 getDetails 메소드에 null 값이 들어가고 있음 이유 모르겠음.. (나중에)
        // LoginUtils.java 메소드 return 값 가져오기  , 아직 처리 못함!!!
        customUserDetails.setUserAuthority(LoginUtils.getAuthority());
        String resultData = customUserDetails.getUserAuthority().getKey();
        System.out.println(" resultData 나오나 ==> " + resultData);
        */


		// 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담아
        // 리스트에 추가한다. userRole 이름은 "ROLE_"로 시작되야 한다.
		List<GrantedAuthority> authorities = new ArrayList<>();     // 해당 사용자가 권한이 여러개 일 수 있기 때문에 list에 담음
		if(customRoles != null) {
			for(UserRoleEntity customRole : customRoles) {
				authorities.add(new SimpleGrantedAuthority(customRole.getRoleName()));  // 시큐리티 SimpleGrantedAuthority 객체는 GrantedAuthority를 상속받고 있음!!!
			}
		}
		
		
		customUserDetails.setAuthorities(authorities); // CustomUserDetails객체에 권한 목록 (authorities)를 설정한다.
		customUserDetails.setEnabled(true);                             // customUserDetails에서 getter return true로 설정가능
		customUserDetails.setAccountNonExpired(true);         // customUserDetails에서 getter return true로 설정가능
		customUserDetails.setAccountNonLocked(true);           // customUserDetails에서 getter return true로 설정가능
		customUserDetails.setCredentialsNonExpired(true); // customUserDetails에서 getter return true로 설정가능

        return customUserDetails;
    }
    


}
