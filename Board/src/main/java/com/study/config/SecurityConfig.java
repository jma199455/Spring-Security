package com.study.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.study.domain.login.CustomAccessDeniedHandler;
import com.study.domain.login.CustomUserDetailService;

@Configuration
@EnableWebSecurity // security 등록 어노테이션 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // WebSecurityConfigurerAdapter 사용하려면 build.gradle plugins 부분 변경


  @Autowired
  CustomUserDetailService customUserDetailService;

  @Autowired
  AuthenticationFailureHandler userLoginFailHandler;

  @Override
  public void configure(WebSecurity web) { // 자원에 대한 접근을 품
    web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    //web.ignoring().antMatchers("/**");
  }
  

  
  // CSRF get, post 확인용으로 설정해본 부분 
  /*  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
  //http.cors().and().csrf()
    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }
  */

  // 스프링 시큐리티 필터 중 AuthenticationFilter가 아이디/암호를 입력해서 로그인 할 때 처리해주는 필터이고 아이디에 해당하는 정보를 데이터베이스에서 읽어 들일 때 UserDetailsService를 구현하고 있는 객체를 이용한다.
  @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService);
	}


  @Override
  protected void configure(HttpSecurity http) throws Exception {
  http
    .exceptionHandling()
        //.authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증실패 시 처리 , .loginPage 작성하고 주석 시 시큐리티 기본 폼으로 넘어감
        .accessDeniedHandler(new CustomAccessDeniedHandler())     // 인가 실패 시(권한불과 페이지 접근 시) 핸들러 설정  , .loginPage주석 시 시큐리티 기본 폼으로 넘어감
    .and()
        .httpBasic() // HTTP 기본 인증을 구성 , 안써도 작동함 사용의도 검색해보기
    .and()
        .csrf().disable() // csrf 토큰 검사 비활성화 , 주석처리하면 로그인 안넘어감 (이유 모르겠음)
        .authorizeRequests() // 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미

        // 권한 페이지 설정 부분
        .antMatchers("/adminOnly").hasAuthority("ROLE_ADMIN") // 페이지 권한 설정(권한)
        .antMatchers("/login2", "/signupForm").permitAll()  // .permitAll() 모든 사용자에 대해 접근(권한)을 허용 설정

        // 권한 페이지 설정 부분
        .antMatchers("/post/list.do").hasRole("ADMIN") // "/admin" 요청은 ADMIN 권한을 가지고 있어야 허용한다 , 시스템상에서 특정 권한을 가진 사람만이 접근할 수 있음
        .anyRequest().authenticated() // 그 외의 요청은 권한이 있기만 하면 허용한다.
    .and()
    .formLogin() // formLogin: 다른 옵션 설정이 없는 경우 시큐리티가 제공하는 기본 로그인 form 페이지 사용
        //.loginPage("/login2").permitAll() // 커스텀 로그인 페이지 설정 , 해당 부분 주석일 경우 기본 로그인 form 페이지 사용 /login
        .defaultSuccessUrl("/chart/main.do")  // 로그인 성공 후 이동 페이지
        //.defaultSuccessUrl("/post/list.do")  // 로그인 성공 후 이동 페이지
        .loginProcessingUrl("/loginAction")// 여기 뭐 들어가야하냐.. 이해 못함 !!!!!!!! 로그인 Form Action Url, 사용자 이름과 암호를 제출할 URL  , default="/login" 
        //.failureHandler(userLoginFailHandler) // 로그인 실패 핸들러 설정
        //.failureUrl("/login2?error=failure") // 로그인 실패 후 이동 페이지

        
        .usernameParameter("id")  // Id input의 name을 이 설정과 맞춰야 한다.          중요!!!!!!! 이부분 다르면 시큐리티 정보 안넘어감 시큐리티 폼과 맞춰야함!!!
        .passwordParameter("pw")  // Password input의 name을 이 설정과 맞춰야 한다.    중요!!!!!!! 이부분 다르면 시큐리티 정보 안넘어감 시큐리티 폼과 맞춰야함!!!
        //.successHandler(new MyloginSuccessHandler())
        .successHandler(new AuthenticationSuccessHandler() {  // 로그인 성공 후 핸들러 (해당 핸들러를 생성하여 핸들링 해준다.)
          @Override
          public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                              HttpServletResponse response, 
                                              Authentication authentication) throws IOException, ServletException {
              System.out.println("authentication:: "+ authentication.getName());
              
              //UsersDto dto = new UsersDto();
              //dto.setId(authentication.getName());


              response.sendRedirect("/chart/main.do");
          }
        })
    .and()
    .logout() // 로그아웃 핸들러 부분 아직 안해봄 !!
        .logoutSuccessUrl("/login2");   // 로그아웃 후 url  , 로그인페이지를 커스텀하게되면 로그아웃을 담당해주던 Filter가 더이상 동작하지 않는다. 그렇기때문에 http.logout().logoutSuccessUrl(성공시 갈 url) 을 적어줘야한다
        //.deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 삭제
        //.invalidateHttpSession(true)
        /*  
        .logoutSuccessHandler((request, response, authentication) -> {
            String accept = StringUtils.defaultString(request.getHeader(HttpHeaders.ACCEPT), "").toLowerCase();
            if(accept.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                System.out.println("if문들어옴 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
                response.sendRedirect("/sessionout");
            }
            else {
                System.out.println("else부분 들어옴 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
                //response.sendRedirect("login2");
            }
        })
        */
        //.permitAll();
  }



  /*  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception { // AuthenticationManagerBuilder는 인증에 대한 다양한 설정을 생성할 수 있다.

    auth.inMemoryAuthentication()
    .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN")
    .and()
    .withUser("guest").password(passwordEncoder().encode("guest")).roles("GUEST");
  }
  */


  // passwordEncoder() 추가
  //패스워드 인코더를 빈으로 등록.
	//암호를 인코딩하거나 인코딩된 암호와 사용자가 입력한 암호가 같은지 확인할때 사용
  @Bean 
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }












}