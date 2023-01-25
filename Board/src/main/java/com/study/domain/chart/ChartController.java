package com.study.domain.chart;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.domain.login.UsersDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChartController {


    // 차트 서비스
    @Autowired 
    private ChartService chartService;


    // 파라미터로 search 등록 (startDt, endDt) 사용하기 위해
    @GetMapping("/chart/main.do")
    public String openPostWrite(ChartRequestVO search, Model model, HttpSession session, HttpServletRequest request, UsersDto UsersDto, Principal principal) throws Exception {
        
        // 차트 페이지 접속시 세션으로 userName 넘기기
        /*  
        Object obj = session.getAttribute("login");
        UsersDto dto = (UsersDto)obj;
        model.addAttribute("userName",dto.getUserName());
        */
        // end 

        List<String> labels = new ArrayList<>();
        System.out.println(labels.size()); // 0

        // 전일 ~일주일 간 방문자수 날짜
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);	// 전일 기준까지
		search.setEndDt(sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, -7);	// 1주일 전 부터
		search.setStartDt(sdf.format(cal.getTime()));

        //StatisticsLineResVO data = chartService.getLine(search);    // 확인
        //model.addAttribute("data", data);
        
        model.addAttribute("data", chartService.getLine(search, "L1")); // 방문횟수
        model.addAttribute("data2", chartService.getLine(search, "L2"));    // 방문자수

        return "chart/main";
    }








}
