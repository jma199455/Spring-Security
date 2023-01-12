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

@Service
public class CustomUserDetailService implements UserDetailsService{


    //데이터베이스에서 로그인 아이디에 해당하는 정보를 읽어 들이기 위해서
    //UserDbService를 구현한 객체를 주입받고 있다.
    @Autowired
    UsersMapper usersMapper;

    //@Autowired
    //private final PasswordEncoder passwordEncoder;

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
        String result = encoder.encode(customUser.getPw()); // 서버 내에서 인코딩해주기 때문에 로딩 시간 오래걸리고 있는 것 같음 , insert할때 암호화 하고 해당 코드 부분 주석처리 해보기
        System.out.println(result);
        // check!!! 비밀번호 암호화 설정 꼭 해야 인증이 넘어감!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        customUserDetails.setUsername(customUser.getId());
        //customUserDetails.setPassword(customUser.getPw()); // 그냥 암호화 없이 UserDetails 넘기면 로그인 인증되지 않음!!!!
        customUserDetails.setPassword(result); // User객체를 리턴해줄때 암호화. 




        List<UserRoleEntity> customRoles = usersMapper.getUserRoles(customUser.getUserSeq());
        System.out.println("customRoles ====> " + customRoles);


		// 로그인 한 사용자의 권한 정보를 GrantedAuthority를 구현하고 있는 SimpleGrantedAuthority객체에 담아
        // 리스트에 추가한다. userRole 이름은 "ROLE_"로 시작되야 한다.
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(customRoles != null) {
			for(UserRoleEntity customRole : customRoles) {
				authorities.add(new SimpleGrantedAuthority(customRole.getRoleName()));
			}
		}
		
		// CustomUserDetails객체에 권한 목록 (authorities)를 설정한다.
		customUserDetails.setAuthorities(authorities);
		customUserDetails.setEnabled(true);
		customUserDetails.setAccountNonExpired(true);
		customUserDetails.setAccountNonLocked(true);
		customUserDetails.setCredentialsNonExpired(true);

        return customUserDetails;
    }
    


}
