package com.example.demo.web;

import com.example.demo.entity.User;
import com.example.demo.service.impl.UserService;
import com.example.demo.util.LTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

/**
 * Created by liuyf90 on 2018/9/26.
 */
@Controller
public class BaseController {
    @Autowired
    private UserService userservice;
    @Autowired
    private LTreeUtil lTreeUtil;
    @ModelAttribute
    public void aop(Model model, Principal principal){
        User u = userservice.getUser(principal.getName());
        model.addAttribute("ltree",lTreeUtil.getCurrentLTree(u.getId()));
    }
}
