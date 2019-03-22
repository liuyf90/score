package com.example.demo.web;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskUser;
import com.example.demo.entity.User;
import com.example.demo.service.impl.UserService;
import com.example.demo.service.impl.TaskService;
import com.example.demo.util.LTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

/**
 * Created by liuyf on 2018/5/6.
 */
@RestController
@RequestMapping("/pool")
public class PoolController extends BaseController{
    @Autowired
    private TaskService taskservice;
    @Autowired
    private UserService userservice;
    @Autowired
    private LTreeUtil lTreeUtil;
    @RequestMapping(value = {"/", "/{id}"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model,Principal principal) {
        model.addAttribute("task", id != null ? taskservice.getOne(id) : null);
        model.addAttribute("taskList", taskservice.findAll());
        return new ModelAndView("user/pool", "taskModel", model);
    }

    @RequestMapping("/pull")
    public String pull(@RequestParam(value = "task_id") Long task_id, @RequestParam(value = "eDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date eDate, @RequestParam(value = "bDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date bDate, Model model, Principal principal) throws Exception {
        Task t = taskservice.getOne(task_id);
        if(bDate==null) {
            Date day = new Date();
            t.setbDate(day);
        }
        t.setbDate(bDate);
        t.seteDate(eDate);
        Set<TaskUser> users = new HashSet<>();
        TaskUser taskUser = new TaskUser();
        taskUser.setUser(userservice.getUser(principal.getName()));
        taskUser.setTask(t);
        taskservice.pull(t, taskUser, userservice.getUser(principal.getName()));
        return "success";
    }
}