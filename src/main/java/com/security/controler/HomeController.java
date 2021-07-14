package com.security.controler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index.html";
    }
    @RequestMapping("/login")
    public String loginPage(){
        return  "login.html";
    }
    @RequestMapping("/succes")
    public String successPage(){
        return  "succes.html";
    }
}