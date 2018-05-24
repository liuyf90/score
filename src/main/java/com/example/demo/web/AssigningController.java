package com.example.demo.web;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.service.impl.ProjectService;
import com.example.demo.service.impl.TaskService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * Created by liuyf90 on 2018/5/24.
 */
@RestController
@RequestMapping("/assigning")
public class AssigningController {
    @Autowired
    private UserService userservice;
    @Autowired
    private TaskService taskservice;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView init(Task task, Model model, Principal principal) {
        User user= userservice.getUser(principal.getName());
        List<Project> projectList=projectService.findByUser(user);
        model.addAttribute("projects",projectList);
        model.addAttribute("task", task.getTaskId() != null ? taskservice.getOne(task.getTaskId()) : null);
        return new ModelAndView("owner/assigning", "taskModel", model);

    }

}
