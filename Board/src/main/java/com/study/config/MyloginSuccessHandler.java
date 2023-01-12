package com.study.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class MyloginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

            HttpSession session = request.getSession();


            System.out.println("authentication.getName() ========================> " + authentication.getName());

            session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
            System.out.println("확인 ==> " + session.getAttribute("greeting"));
            // 가능한가
            ModelAndView model = new ModelAndView();
            model.addObject("greeting", session.getAttribute("greeting"));
            
            // response.sendRedirect("/");
        
    }
    
    
}
