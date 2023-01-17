package com.study.domain.login;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.common.dto.MessageDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UsersController {
    
    @Autowired
    UsersService usersService;

    @GetMapping("/signupForm")
    public String signupForm() {    

    return "login/signup";
    }

    @PostMapping("/signup")
    public String signUpProc(@ModelAttribute UsersDto params, Model model) {    

        return "redirect:/login";
    }


    @GetMapping("/login2")
    public String openLoginForm(@RequestParam(value = "error", required = false) String param,
                                @RequestParam(value = "exception", required = false) String exception
                                , Model model) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        System.out.println("error ====================> " + param);
        System.out.println("exception ====================> " + exception);

        if (param != null) {
            System.out.println("들어감ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
            model.addAttribute("data", param);
            model.addAttribute("exception", exception);

            //return "login/loginForm";
            return "login/loginForm";
        }

        return "login/loginForm";
    }












    /*  
    // loginForm.html 하나로만 error 문구 띄우기
    //@GetMapping({"/", "/login2"})    // 2개 설정 , "/" 는 시큐리티 설정에서 자동으로 /login 으로 가도록 설정해줌
    @GetMapping("/login2")
    public String openLoginForm(@RequestParam(value = "error", required = false) String param, Model model) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        System.out.println("parammmmmmmmmmmmmm ====================> " + param);
        if (param != null) {
            System.out.println("들어감ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
            model.addAttribute("data", param);
            //return "login/loginForm";
            return "login/loginForm";
        }

        return "login/loginForm";
    }
    */


















    // 확인 후 지우기
    /*  
   @GetMapping("/")
    public String openLoginFormA(Model model, HttpSession session) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        int temp = 123;

        Object obj = session.getAttribute("login");
        System.out.println("obj ========> " + obj);

        model.addAttribute("data", temp);
        

        return "homeTest";
    }
    */

    /*  
    @PostMapping("/homeTestAction")
    public String openLoginFormAction(@ModelAttribute UsersDto params) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 
        System.out.println("params =============> " + params);

        return "homeTest";
    }
    */

    // .loginProcessingUrl("/loginAction") 사용해서 시큐리티 로그인 정보 넘어가는지 확인하려고 만듬 --> 해당 컨트롤러 타지 못해서 확인 못함...
    @PostMapping("/loginAction")
    public String openLoginAction(UsersDto UsersDto) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        System.out.println("UsersDto ==============> " + UsersDto);
        System.out.println("action들어옴");
        //UsersDto dto = usersService.getUser(UsersDto);

        return "login/loginFormAction";
                
    }



    // 시큐리티 사용 전 로그인 체크
    //@PostMapping("/loginCheck")
    @Transactional
    @PostMapping("/loginCheck")                     // form 데이터 받을 때 @modelAttrubute 안써도 가능한지 다시 확인
    public String loginCheck(HttpSession session, UsersDto UsersDto, RedirectAttributes redirect) throws Exception {    // RedirectAttributes redirect 리다이렉트 파라미터 보내기

        System.out.println("loginCheck 실행ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
        System.out.println("usersDto ======> " + UsersDto);

        String returnURL = "";

        if (session.getAttribute("login") != null) {
            session.removeAttribute("login");
        }
        
        UsersDto dto = usersService.getUser(UsersDto);
        System.out.println("dto ===================> " + dto);

        if (dto != null) {
            session.setAttribute("login", dto); 
            //returnURL = "redirect:/post/list.do";

            // 로그인시 로그인 테이블 등록
            int result = usersService.insert(dto);
            System.out.println("result ================> " + result);
            if (result > 0) {
                //returnURL = "redirect:/post/list.do";
                returnURL = "redirect:/chart/main.do";
            }

        } else {
            System.out.println("else!!!");
            String str = "failure";
            redirect.addAttribute("error",str); // 리다이렉트 파라미터 보내기
            returnURL = "redirect:/login";
        }

        return returnURL;
        //return "/post/list.do"; 안되는 이유확인
    }


    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }

    // redirect 클래스 사용해서 logout 하기
    @GetMapping("/logout.do")
    public String logout(HttpSession session, Model model) {

        System.out.println("로그아웃 컨트롤러 들어옹ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");

        session.invalidate(); // 세션 초기화 (시큐리티에서 세션만 제거하면 되는거 같음)
        MessageDto message = new MessageDto("로그아웃이 완료되었습니다.", "/login2222", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    } 

    /*  
    // 시큐리티 예외처리 설정 안되면 제거 , 시큐리티 exceptionHandling 사용해보려고 작성 클래스  ===> 작동안됨
    @GetMapping("/auth/not-secured")
    public ResponseEntity notSecured(){

        System.out.println("에렁ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ들어감 ");
        return new ResponseEntity(
                new Response(401, "로그인이 되지 않았습니다."), HttpStatus.UNAUTHORIZED
        ); // 권한이 여러개일 경우에는 따로 설정해주어야 한다.
    }
    */


    // 권한이 없는 페이지 접근 시 
    @GetMapping("/errorAction")
    public String getCustomAccessDeniedHandler(HttpSession session, Model model) {

        System.out.println("==========권한없는 페이지 로그 확인==========");
        return "error/authorityError";

    } 





    
    


}
