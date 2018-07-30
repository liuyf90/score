package com.example.demo.web;

import com.example.demo.dao.TaskRepository;
import com.example.demo.dao.TaskUserRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

/**
 * Created by liuyf90 on 2018/7/23.
 */
@RestController
@RequestMapping("/analyze")
public class AnalyzeController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private TaskRepository taskRepository;
        @RequestMapping(value = {"/"}, method = RequestMethod.GET)
        public ModelAndView init(@ModelAttribute("taskInfo") Task task, Model model, Principal principal) {
            model.addAttribute("ltree", 5);
            List<User> userList=userRepository.findAll();
            Iterator<User> userIterator=userList.iterator();
            while(userIterator.hasNext()){
                List<Role> roles=(List<Role>)userIterator.next().getAuthorities();
                Iterator<Role> iterator=roles.iterator();
                while(iterator.hasNext()){
                    if(iterator.next().getAuthority().equals("ROLE_ADMIN")){
                        userIterator.remove();
                        break;
                    }
                }
            }
            String[] users=new String[userList.size()];
            for(int i=0;i<=userList.size()-1;i++){
                users[i]=userList.get(i).getCname();
            }
            model.addAttribute("users",users);
            int[] taskCount=new int[userList.size()];
            int[] taskFinallyCount=new int[userList.size()];
            for(int i=0;i<=userList.size()-1;i++){
                List<TaskUser> t=taskUserRepository.findByUserId(userList.get(i).getId());
                taskCount[i]=t.size();
                Iterator<TaskUser> taskUserIterator=t.iterator();
                while(taskUserIterator.hasNext()){
                    TaskUser taskUser=taskUserIterator.next();
                    if((taskUser.getTask().getFinish()!=TaskStatus.PASS.getIndex())){
                        taskUserIterator.remove();
                    }
                }
                taskFinallyCount[i]=t.size();
            }

            model.addAttribute("taskCount",taskCount);
            model.addAttribute("taskFinallyCount",taskFinallyCount);
            return new ModelAndView("owner/analyze", "taskModel", model);
        }
}
