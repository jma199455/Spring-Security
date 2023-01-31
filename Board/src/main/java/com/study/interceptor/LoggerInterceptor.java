package com.study.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 로그인처리를 담당하는 인터셉터
public class LoggerInterceptor implements HandlerInterceptor{
    
    // preHandle() : 컨트롤러보다 먼저 수행되는 메서드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { // 매개변수 Object는 핸들러 정보를 의미한다.
        log.debug("==================== Interceptor BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());
        log.debug("===========================================================");

        HttpSession session =  request.getSession();
        Object obj = session.getAttribute("login");
        //System.out.println(obj);

        if (obj == null) {
            // 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
            System.out.println("리다이렉트로?????");
            response.sendRedirect("/login");   // 수정필요 /login 으로!!!!!!
            return false; // 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
        }


        // preHandle의 return은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
        // 따라서 true로하면 컨트롤러 uri로 가게 됨.
        return true;
        
        // return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    // 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드 , 즉 화면(View)으로 결과를 전달하기 전에 실행되는 메서드입니다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("==================== Interceptor END ======================");
        log.debug("modelAndView ===> " + modelAndView);
        log.debug("===========================================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    
    // afterCompletion 화면에 보여지고 수행되는 메서드
}
