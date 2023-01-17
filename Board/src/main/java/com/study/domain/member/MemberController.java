package com.study.domain.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

        // 권한이 없는 페이지 접근 시 
        @GetMapping("/member/list.do")
        public String getMemberListForm() {
    
            System.out.println("==========확인==========");
            return "member/list";
    
        } 




}
