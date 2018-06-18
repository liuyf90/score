package com.example.demo.web;

import com.example.demo.entity.Project;
import com.example.demo.entity.Role;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.service.impl.ProjectService;
import com.example.demo.service.impl.TaskService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.BindingType;
import java.security.Principal;
import java.util.*;

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
    public ModelAndView init(@ModelAttribute("taskInfo")  Task task, Model model, Principal principal) {
        User user= userservice.getUser(principal.getName());
        List<Role> roles=userservice.searchRoles(user.getUsername());
        List<Project> projectList=null;
        Iterator<Role> iterator=roles.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getAuthority().equals("ROLE_ADMIN")){
                 projectList=projectService.findAll();
            }
        }
        if(projectList==null){
            projectList=projectService.findByUser(user);
        }
        model.addAttribute("projects",projectList);
        model.addAttribute("task", task.getTaskId() != null ? taskservice.getOne(task.getTaskId()) : null);
        return new ModelAndView("owner/assigning", "taskModel", model);

    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.GET)
    public ModelAndView save(@ModelAttribute("taskInfo") @Validated Task task, BindingResult result, Model model, Principal principal ) {
        if(result.hasErrors()){
            List<ObjectError> allErrors=result.getAllErrors();
            for(ObjectError ob:allErrors){
                System.out.println(ob.getDefaultMessage());
            }
            model.addAttribute("errors",allErrors);
            return init(task,model,principal);
        }
        model.addAttribute("task",null);
        task.setcDate(new Date());
        task.setUser(userservice.getUser(principal.getName()));
        taskservice.save(task);
        User user=new User();
        user.setSts(1);
        user.setUsername(principal.getName());
        User user1 =userservice.findAll(user).get(0);
        Map<Integer,String> status=new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("待测试",4);
        status.put(0,"未领取");
        status.put(1,"处理中");
        status.put(2,"提交待审核");
        status.put(3,"已审核");
        status.put(4,"待测试");
        model.addAttribute("taskstatus",status);
        model.addAttribute("users",userservice.findAll(null));
//        model.addAttribute("taskList", taskservice.assignedTasks(user1.getId()));

        return new ModelAndView("owner/checkTasks", "taskModel", model);
    }

}
