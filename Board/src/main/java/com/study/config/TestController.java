package com.study.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class TestController {
  
  @RequestMapping("/home")
  public String home(ModelAndView mav) {
    return "home";  
  }
  
  @ResponseBody
  @RequestMapping("/test")
  public String test() {
    return "OK";
  }
  
  @ResponseBody
  @RequestMapping("/adminOnly")
  public String adminOnly() {
    return "Secret Page";
  }
}