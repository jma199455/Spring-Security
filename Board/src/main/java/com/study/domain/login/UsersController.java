package com.study.domain.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.common.dto.MessageDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UsersController {
    
    @Autowired
    UsersService usersService;

    // // 회원 가입 폼 
    @GetMapping("/signupForm")
    public String signupForm() {    

    return "login/signup";
    }

    // 회원 가입 처리 ajax 처리 (반환값 String이면 에러남!!)
    @ResponseBody
    @PostMapping("/signup")
    public int signUpProc(@RequestBody UsersDto params, Model model) throws Exception {    
        System.out.println("컨트롤러 확인");

        int result = usersService.save(params);
        return result;
    }

    // 아아디 중복 검사
    @ResponseBody
    @PostMapping("/idCheck")
    public int idCheckProc(@RequestBody UsersDto params) throws Exception {    
        System.out.println("아이디 중복검사");
        int result = usersService.idCheck(params);
        return result;
    }
   




    // 시큐리티 커스텀 로그인폼 처리 , 시큐리티 설정에서 .failureUrl("/login2?error=failure") 사용 안하고 지금은 기본 시큐리티 /error 처리 했음
    //@GetMapping({"/", "/login2"})
    @GetMapping("/login2")     // 파라미터 이름이과  해당 url 변수 이름과 같으면 @RequestParam 생략 가능 !!
    public String openLoginForm(String error, Model model) {    // 스프링 시큐리티로 적용
                            

        System.out.println("확인 ====================> ");
        System.out.println("String error ======> " + error);

        // 해당 if문은 error 파라미터 자체가 null 일 경우 if문 수행할 때 오류가 난다.
        if (error != null && error.equals("")) {
            model.addAttribute("data", "failure22");
        }

        // 공백 검수 방법 
        /*  
        if (!StringUtils.isEmpty(error)) {
            model.addAttribute("data", error);     
        }   
        */

        return "login/loginForm";


    }

    /*  
    // 시큐리티 커스텀 로그인폼 처리 시큐리티 설정에서 .failureUrl("/login2?error=failure") 설정 했을 때 
    // loginForm.html 하나로만 error 문구 띄우기 , 
    //@GetMapping({"/", "/login2"})    // 2개 설정 , "/" 는 시큐리티 설정에서 자동으로 /login 으로 가도록 설정해줌
    @GetMapping("/login2")
    public String openLoginForm(@RequestParam(value = "error", required = false) String param, Model model) {    

        System.out.println("parammmmmmmmmmmmmm ====================> " + param);
        if (param != null) {
            System.out.println("들어감ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
            model.addAttribute("data", param);
            return "login/loginForm";
        }

        return "login/loginForm";
    }
    */

    
    // .loginProcessingUrl("/loginAction") 사용해서 시큐리티 로그인 정보 넘어가는지 확인하려고 만듬 --> 직접 구현하는 것이 아니라 스프링 시큐리티 필터가 자동으로 처리해준다. 
    /*
    @PostMapping("/loginAction")
    public String openLoginAction(UsersDto UsersDto) {    // @RequestParam지우고 스프링 시큐리티로 적용시켜보기 

        System.out.println("UsersDto ==============> " + UsersDto);
        System.out.println("action들어옴");
        //UsersDto dto = usersService.getUser(UsersDto);

        return "login/loginFormAction";
                
    }
    */


    // 시큐리티 사용 전 인터셉터 사용해서 로그인 체크 로직
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
        MessageDto message = new MessageDto("로그아웃이 완료되었습니다.", "/login2222", RequestMethod.GET, null);   // 여기 적용 안되는거 같음 다시 확인
        //return showMessageAndRedirect(message, model); // 여기 적용 안되는거 같음 다시 확인
        return "redirect:/login2";
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


    // 권한이 없는 페이지 접근 시 처리
    @GetMapping("/errorAction")
    public String getCustomAccessDeniedHandler(HttpSession session, Model model) {

        System.out.println("==========권한없는 페이지 로그 확인==========");
        return "error/authorityError";

    } 

    // 마스터 권한 페이지 접근 시 
    @GetMapping("/master/list.do")
    public String openMasterList() {

        System.out.println("==========마스터 페이지 로그 확인==========");
        return "admin/masterList";

    } 

    

    
    


}
