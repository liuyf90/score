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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = {"/save"}, method = RequestMethod.GET)
    public ModelAndView save(Task task, Model model, Principal principal) {
        task.setcDate(new Date());
        task.setUser(userservice.getUser(principal.getName()));
        taskservice.save(task);


        User user=new User();
        user.setSts(1);
        user.setUsername(principal.getName());
        User user1 =userservice.findSearch(user).get(0);
        Map<Integer,String> status=new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("待测试",4);
        status.put(0,"未领取");
        status.put(1,"处理中");
        status.put(2,"提交待审核");
        status.put(3,"已审核");
        status.put(4,"待测试");
        model.addAttribute("taskstatus",status);
        model.addAttribute("task", null);
        model.addAttribute("users",userservice.findAll());
        model.addAttribute("taskList", taskservice.assignedTasks(user1.getId()));
        return new ModelAndView("owner/checkTasks", "taskModel", model);
    }

}
