package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by liuyf on 2018/5/3.
 */
@SpringBootApplication
@Controller
public class RecordThePointsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecordThePointsApplication.class, args);
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/pool/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/401")
    public String accesssDenied() {
        return "401";
    }
}
