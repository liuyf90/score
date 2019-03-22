package com.example.demo.web;

import com.example.demo.dao.ServerRepository;
import com.example.demo.entity.*;
import com.example.demo.service.impl.ProjectService;
import com.example.demo.service.impl.ServerService;
import com.github.pagehelper.PageInfo;
import org.codehaus.groovy.tools.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyf90 on 2018/9/12.
 */
@RestController
@RequestMapping("/server")
public class ServerController extends BaseController{
    @Autowired
    ServerService serverService;
    @Autowired
    ProjectService projectService;

    @ModelAttribute
    public void myModel(Model model, Principal principal) {
        model.addAttribute("errors",null);
        Map<Integer, String> system = new HashMap<>();
        system.put(SystemEnum.CENTOS6.getIndex(),SystemEnum.CENTOS6.getName());
        system.put(SystemEnum.CENTOS7.getIndex(),SystemEnum.CENTOS7.getName());
        system.put(SystemEnum.WINDOWS7.getIndex(),SystemEnum.WINDOWS7.getName());
        system.put(SystemEnum.WINDOWS10.getIndex(),SystemEnum.WINDOWS10.getName());
        system.put(SystemEnum.WINDOWSSERVER.getIndex(),SystemEnum.WINDOWSSERVER.getName());
        model.addAttribute("system", system);

    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView init(@ModelAttribute("serverInfo") Server serverInfo,  @PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("serverList", projectService.getOne(id).getServerList());
        model.addAttribute("projectId", id);
        model.addAttribute("projectName", projectService.getOne(id).getProjectName());
        return new ModelAndView("owner/server", "taskModel", model);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Page<Server> query(Server server, Model model, Principal principal, com.example.demo.entity.PageInfo<Server> pageInfo) {
        Project p = new Project();
        p.setProjectId(server.getProjectId());
        Page<Server> page = serverService.findSearch(p, pageInfo);
        return page;
    }

    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
    public Server one(@PathVariable(value = "id", required = false) Long id, Principal principal) {
        return serverService.getOne(id);
    }
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("serverInfo")@Validated Server serverInfo,BindingResult result,Server server, Model model) {
        model.addAttribute("projectId", server.getProjectId());
        model.addAttribute("projectName", projectService.getOne(server.getProjectId()).getProjectName());
        if(result.hasErrors()){
            List<ObjectError> allErrors=result.getAllErrors();
            for(ObjectError ob:allErrors){
                System.out.println(ob.getDefaultMessage());
            }
            model.addAttribute("errors",allErrors);
            return new ModelAndView("owner/server", "taskModel", model);
        }
        if(serverInfo.getServerId()==null){//新增
            serverInfo.setProject(projectService.getOne(serverInfo.getProjectId()));
            serverService.save(serverInfo);
            model.addAttribute("serverList", projectService.getOne(serverInfo.getProjectId()).getServerList());
            model.addAttribute("projectId", serverInfo.getProjectId());
            model.addAttribute("projectName", projectService.getOne(serverInfo.getProjectId()).getProjectName());
        }else{//修改
            Server s=serverService.getOne(serverInfo.getServerId());
            s.setIp(serverInfo.getIp());
            s.setUsed(serverInfo.getUsed());
            s.setSystem(serverInfo.getSystem());
            s.setPort(serverInfo.getPort());
            s.setUserName(serverInfo.getUserName());
            s.setPassword(serverInfo.getPassword());
            serverService.edit();
        }
        return new ModelAndView("owner/server", "taskModel", model);
    }
}
