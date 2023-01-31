package com.study.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestFilter implements Filter{
    

    private FilterConfig filterConfig = null;

    // filter 생성 시 처리
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        log.debug("====================init====================");
    }

     
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                HttpSession session = request.getSession(); // 접속경로
                log.debug("==========First 필터 시작!==========");
                log.debug("doFilter , Request URL is : " + request.getRequestURI());    // 요청 url
                chain.doFilter(req, res);   // Request, Response가 필터를 거칠 때 수행되는 메소드
                log.debug("==========First 필터 종료!==========");
                log.debug("Response Status Code is : " + response.getStatus());
                
    }

    @Override
    public void destroy() {
        log.debug("====================destroy====================");    
    }
    

    
}
