package com.example.demo.web;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.entity.User;
import com.example.demo.service.impl.TaskService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Date;
import java.util.List;
/**
 * Created by liuyf on 2018/5/6.
 */
@RestController
@RequestMapping("/mytasks")
public class MyTasksController {
    @Autowired
    private UserService userservice;
    @Autowired
    private TaskService taskservice;
    @RequestMapping(value = {"/", "/{id}"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model, Principal principal){
        User user=new User();
        user.setSts(1);
        user.setUsername(principal.getName());
        User user1 =userservice.findAll(user).get(0);
        List<Task> tasks=user1.getTasks();
        model.addAttribute("task", id != null ? taskservice.getOne(id) : null);
        model.addAttribute("taskList", tasks);

        Long count=this.taskservice.score(user1);
        model.addAttribute("myTaskcount", count);
        return new ModelAndView("user/myTasks", "taskModel", model);

    }
    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String  done(@RequestParam(value = "task_id") Long task_id){
        Task task=taskservice.getOne(task_id);
        taskservice.done(task,TaskStatus.FINISH);
        return "success";
    }



}