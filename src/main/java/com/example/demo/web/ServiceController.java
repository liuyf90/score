package com.example.demo.web;

import com.example.demo.dao.ProcessRepository;
import com.example.demo.entity.Process;
import com.example.demo.entity.Project;
import com.example.demo.entity.Server;
import com.example.demo.service.impl.ServerService;
import com.example.demo.service.impl.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.security.Principal;
import java.util.List;

/**
 * 服务实例
 * Created by 刘宇飞 on 2019-05-30.
 */
@RestController
@RequestMapping("/service")
public class ServiceController extends BaseController {
    @Autowired
    ServiceService serviceService;
    @Autowired
    ServerService serverService;
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView init(@ModelAttribute("serviceInfo") Process serverInfo, @PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("serviceList", serverService.getOne(id).getServices());
        model.addAttribute("serverId", id);
        model.addAttribute("projectId", serverService.getOne(id).getProject().getProjectId());
        return new ModelAndView("owner/service", "taskModel", model);
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("serviceInfo")@Validated Process serviceInfo, BindingResult result, Model model) {
        model.addAttribute("serverId", serviceInfo.getServerId());
        model.addAttribute("projectId", serverService.getOne(serviceInfo.getServerId()).getProject().getProjectId());

        if(result.hasErrors()){
            List<ObjectError> allErrors=result.getAllErrors();
            for(ObjectError ob:allErrors){
                System.out.println(ob.getDefaultMessage());
            }
            model.addAttribute("errors",allErrors);
            return new ModelAndView("owner/service", "taskModel", model);
        }

        if(serviceInfo.getServiceId()==null) {//新增
            serviceInfo.setServer(serverService.getOne(serviceInfo.getServerId()));
            serviceService.save(serviceInfo);
        }else{
            Process service=serviceService.getOne(serviceInfo.getServiceId());
            service.setName(serviceInfo.getName());
            service.setPort(serviceInfo.getPort());
            service.setRemark(serviceInfo.getRemark());
            serviceService.edit(service);
        }
        return new ModelAndView("owner/service", "taskModel", model);
    }
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Page<Process> query(Process process, Model model, Principal principal, com.example.demo.entity.PageInfo<Process> pageInfo) {
        Page<Process> page = serviceService.findSearch(process, pageInfo);
        return page;
    }

    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
    public Process one(@PathVariable(value = "id", required = false) Long id, Principal principal) {
        return serviceService.getOne(id);
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public ModelAndView del(@PathVariable(value = "id", required = false) Long id, Principal principal) {
        Process p=serviceService.getOne(id);
        String s="redirect:/service/"+p.getServer().getServerId();
        serviceService.del(p);
        return new ModelAndView(s);

    }

    @RequestMapping(value = "/direct/{id}", method = RequestMethod.GET)
    public Page<Process> direct(Process process,@PathVariable(value = "id", required = false) Long id, com.example.demo.entity.PageInfo<Process> pageInfo) {
         Page<Process> page = serviceService.findSearch(process , pageInfo);
         return page;

    }
}
