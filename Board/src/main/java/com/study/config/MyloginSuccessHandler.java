package com.study.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.ModelAndView;

import com.study.domain.login.CustomUserDetails;

public class MyloginSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private CustomUserDetails customUserDetails;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

            HttpSession session = request.getSession();


            System.out.println("authentication.getName() ========================> " + authentication.getName());

            session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
            System.out.println("확인 ==> " + session.getAttribute("greeting"));

            


            System.out.println("aaaaaaaaaaaaaaaaaa =======> " + authentication.getAuthorities());
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                authorities.add(grantedAuthority);
            }
            System.out.println("authoritiesauthoritiesauthorities ====> " + authorities.get(0)); // authorities 값으로 session으로 넣어줄 수 있음
            //customUserDetails.setAuthorities(authorities); null 값나옴..


            // 가능한가
            ModelAndView model = new ModelAndView();
            model.addObject("greeting", session.getAttribute("greeting"));
            response.sendRedirect("/chart/main.do");
            // response.sendRedirect("/");
        
    }
    
    
}
