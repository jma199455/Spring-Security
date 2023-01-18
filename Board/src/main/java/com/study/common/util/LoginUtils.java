package com.study.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.study.domain.login.CustomUserDetails;
import com.study.domain.login.UserAuthority;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class LoginUtils {	// SpringContextHolder를 이용해서 사용자 정보 알아오기.
    
	/**
	 * 로그인한 사용자 권한 가져오기
	 * 
	 * @return
	 */
	public static UserAuthority getAuthority() {
		CustomUserDetails details = getDetails();
		if(details == null) {
			return null;
		}
		return details.getUserAuthority();
	}

    /**
	 * CustomUserDetails 클래서 UserDetails 상속
	 * details 반환
	 * @return details
	 */
	public static CustomUserDetails getDetails() {
		Object principal = null;
        CustomUserDetails customUserDetails = null; 
		try {
			principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();	// 여기 에러....................
			//principal = SecurityContextHolder.getContext().getAuthentication();						// null 값이 들어오고있음 

			System.out.println("principal111111111111111111111 =============> " + principal);		// 데이터 확인해봐야함
            customUserDetails = (CustomUserDetails)principal;

            customUserDetails.getUsername();
            customUserDetails.getPassword();
            
            System.out.println("principal22222222222222222222 ===============> "  + principal);     // 데이터 확인해봐야함


			//details = (UserDetailsImpl) principal;  // 왜 안들어갈까
			//System.out.println("확ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ : " + details);
			//return details;
            return customUserDetails;
		}
		catch(Exception e) {
			log.debug("principal : {}", principal);
		}
		//return details;
        return customUserDetails;
	}

}
