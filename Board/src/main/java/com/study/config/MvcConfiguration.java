package com.study.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.study.filter.TestFilter;
import com.study.interceptor.LoggerInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer { // 해당 인터페이스를 구현하면 @EnableWebMvc의 자동 설정을 베이스로 가져감

	/*  
	정리전
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor()) // InterceptorRegistry의 addInterceptor( ) 메서드를 이용하여 인터셉터 클래스를 등록
		.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/images/**", "/js/**", "/login", "/loginCheck");	// 인터셉터 호출에서 제외할 URL 또는 PATH  , addPathPatterns( )는 경로 추가.
	}
	*/
	
	@Override
	// 애플리케이션 내에 인터셉터를 등록해주는 기능.
	public void addInterceptors(InterceptorRegistry registry) {
		
		final String[] excludePath = {
			"/css/**", 
			"/fonts/**", 
			"/plugin/**", 
			"/scripts/**", 
			"/images/**", 
			"/js/**", 
			"/login", 
			"/loginCheck",
			"/logout",
			"/signupForm",
			"/signup",	// 여기까지 인터셉터로 로그인 처리하기 위해서 제외 url 설정한 부분  (/loginCheck 사용해서 로그인 처리 했을 때)

			// 시큐리티 사용해보려고 인터셉터 적용 하지 않을려고 작성
			"/**"    
			
		};

		// InterceptorRegistry의 addInterceptor( ) 메서드를 이용하여 인터셉터 클래스를 등록
		// 인터셉터 호출에서 제외할 URL 또는 PATH  , addPathPatterns( )는 경로 추가.
		registry.addInterceptor(new LoggerInterceptor()).excludePathPatterns(excludePath);	
	}



	// CommonsMultipartResolver Bean 등록 (다중파일 업로드에 사용)
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
		multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 파일당 업로드 크기 제한 (5MB)
		return multipartResolver;
	}

	// filter bean 등록
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new TestFilter());
		registrationBean.setOrder(Integer.MIN_VALUE); // 여러 개의 필터가 있을 때, 필터의 동작 순서를 결정
		//registrationBean.addUrlPatterns("/post/*","/chart/*"); // string 여러개를 가변인자로 받는 메소드 , setUrlPatterns과 같다
		registrationBean.setUrlPatterns(Arrays.asList("/post/*","/chart/*")); // addUrlPatterns("/*"); 과 같다  , list로도 받을 수 있음 .setUrlPatterns(Collections.singletonList("/post/*"));
		return registrationBean;
	}




}
