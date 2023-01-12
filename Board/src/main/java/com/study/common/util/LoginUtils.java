package com.study.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.study.domain.login.UserDetailsImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginUtils {
    
	/**
	 * 로그인한 사용자 권한
	 * 
	 * @return
	 */

    /*  
	public static UserAuthority getAuthority() {
		UserDetailsImpl details = getDetails();
		if(details == null) {
			return null;
		}
		return details.getAuthority();
	}
    */

    /**
	 * details 반환
	 * 
	 * @return details
	 */
	public static UserDetails getDetails() {
		Object principal = null;
		//UserDetailsImpl details = null;
        UserDetails userDetails = null;

		try {
			principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userDetails = (UserDetails)principal;

            userDetails.getUsername();
            userDetails.getPassword();
            
            System.out.println("principal ===============> "  + principal);
			//details = (UserDetailsImpl) principal;  // 왜 안들어갈까
			//System.out.println("확ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ : " + details);
			//return details;
            return userDetails;
		}
		catch(Exception e) {
			log.debug("principal : {}", principal);
		}
		//return details;
        return userDetails;
	}

}
