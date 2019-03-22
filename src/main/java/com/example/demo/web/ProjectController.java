package com.example.demo.web;

import com.example.demo.dao.ProjectRepository;
import com.example.demo.entity.Project;
import com.example.demo.entity.Role;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.service.impl.ProjectService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyf90 on 2018/9/11.
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController{
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    @ModelAttribute
    public void myModel(Model model, Principal principal) {
        model.addAttribute("projectInfo",null);
        model.addAttribute("owners",userService.searchAllOwners());
    }
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView init( Model model, Principal principal) {
        return new ModelAndView("owner/project", "taskModel", model);

    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Page<Project> query(Model model, Principal principal, com.example.demo.entity.PageInfo<Project> pageInfo) {
        Page<Project> ps= projectService.findSearch(null,pageInfo);
        return ps;
    }

    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
    public Project one(@PathVariable(value = "id", required = false) Long id, Principal principal) {
       return projectService.getOne(id);
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ModelAndView save( Project project,Model model) {
        if(project.getProjectId()==null){//新增
            projectService.save(project);
        }else{//修改
            Project p=projectService.getOne(project.getProjectId());
            p.setProjectName(project.getProjectName());
            User user=userService.getOne(project.getOwner());
            p.setUser(user);
            projectService.edit();
        }
        return new ModelAndView("owner/project", "taskModel", model);
    }
}
