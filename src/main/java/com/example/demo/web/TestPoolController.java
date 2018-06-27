package com.example.demo.web;

import com.example.demo.entity.TaskStatus;
import com.example.demo.service.impl.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyf90 on 2018/6/27.
 */
@RestController
@RequestMapping("/test")
public class TestPoolController {
    @Autowired
    private TaskService taskservice;
    @ModelAttribute
    public void myModel(Model model,Principal principal){
        Map<Integer,String> status=new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("待测试",4);
//        status.put(0,"未领取");
        status.put(1,"处理中");
        status.put(2,"提交待审核");
        status.put(3,"已审核");
        status.put(4,"待测试");
        model.addAttribute("taskstatus",status);
    }
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("taskList", taskservice.findByFinish(TaskStatus.CHECK));
        return new ModelAndView("/tester/testPool", "taskModel", model);
    }
}
