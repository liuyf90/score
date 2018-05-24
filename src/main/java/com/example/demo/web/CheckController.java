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
import java.util.*;

/**
 * Created by liuyf90 on 2018/5/20.
 */
@RestController
@RequestMapping("/check")
public class CheckController {
    @Autowired
    private UserService userservice;
    @Autowired
    private TaskService taskservice;
    @RequestMapping(value = {"/", "/{id}"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model, Principal principal){
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
        model.addAttribute("task", id != null ? taskservice.getOne(id) : null);
        model.addAttribute("users",userservice.findAll());
        model.addAttribute("taskList", taskservice.assignedTasks(user1.getId()));
        return new ModelAndView("owner/checkTasks", "taskModel", model);
    }
    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String  done(@RequestParam(value = "task_id") Long task_id){
        Task task=taskservice.getOne(task_id);
        task.setCheckDate(new Date());
        task.setFinish(TaskStatus.CHECK);
         taskservice.update2(task);
        return "success";
    }
    @RequestMapping(value = "/assigned", method = RequestMethod.GET)
    public String assigned(@RequestParam(value = "user_id") Long user_id,@RequestParam(value = "task_id") Long task_id){
        Task task=taskservice.getOne(task_id);
        task.setbDate(new Date());
        task.setFinish(TaskStatus.DONE);
        User user=new User();
        user.setId(user_id);
        Set<User> users=new HashSet<>();
        users.add(user);
        task.setReceivers(users);
        taskservice.update2(task);
        return "success";
    }
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query(Task task, Model model, Principal principal){
        model.addAttribute("taskList",  taskservice.findSearch(task));
        model.addAttribute("task",  null);
        Map<Integer,String> status=new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("待测试",4);
        status.put(0,"未领取");
        status.put(1,"处理中");
        status.put(2,"提交待审核");
        status.put(3,"已审核");
        status.put(4,"待测试");
        model.addAttribute("taskstatus",status);
        model.addAttribute("users",userservice.findAll());
        return new ModelAndView("owner/checkTasks", "taskModel", model);
    }


}
