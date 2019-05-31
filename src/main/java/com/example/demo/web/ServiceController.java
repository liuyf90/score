package com.example.demo.web;

import com.example.demo.dao.ProcessRepository;
import com.example.demo.entity.Process;
import com.example.demo.entity.Server;
import com.example.demo.service.impl.ServerService;
import com.example.demo.service.impl.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


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
        return new ModelAndView("owner/service", "taskModel", model);
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("serviceInfo")@Validated Process serviceInfo, BindingResult result, Model model) {
        if(result.hasErrors()){
            List<ObjectError> allErrors=result.getAllErrors();
            for(ObjectError ob:allErrors){
                System.out.println(ob.getDefaultMessage());
            }
            model.addAttribute("errors",allErrors);
            return new ModelAndView("owner/service", "taskModel", model);
        }
        serviceInfo.setServer(serverService.getOne(serviceInfo.getServerId()));
        serviceService.save(serviceInfo);
        model.addAttribute("serverId", serviceInfo.getServerId());
        return new ModelAndView("owner/service", "taskModel", model);
    }
}
