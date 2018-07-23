package com.example.demo.web;

import com.example.demo.entity.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyf90 on 2018/7/23.
 */
@RestController
@RequestMapping("/analyze")
public class AnalyzeController{
        @RequestMapping(value = {"/"}, method = RequestMethod.GET)
        public ModelAndView init(@ModelAttribute("taskInfo") Task task, Model model, Principal principal) {
            model.addAttribute("ltree", 5);
            return new ModelAndView("owner/analyze", "taskModel", model);

        }
}
