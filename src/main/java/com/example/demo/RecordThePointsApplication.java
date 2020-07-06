package com.example.demo;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by liuyf on 2018/5/3.
 */
@SpringBootApplication
@Controller
@EnableJpaAuditing
public class RecordThePointsApplication {
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(RecordThePointsApplication.class, args);
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/pool/";
    }

    @RequestMapping("/login")
    public String login() {
        return "index";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "index";
    }
    @RequestMapping("/editPassword")
    @ResponseBody
    public String editPassword(@RequestParam(value = "passwd") String  password,Principal principal) {
       User user= userRepository.findByUsername(principal.getName());
       user.setPassword(password);
       try {
           userRepository.save(user);
       }catch (Exception e){
           return "error";
       }
        return "success";
    }


    @GetMapping("/401")
    public String accesssDenied() {
        return "401";
    }
}
