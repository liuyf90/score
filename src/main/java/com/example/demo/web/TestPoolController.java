package com.example.demo.web;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.entity.TestReport;
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
 * Created by liuyf90 on 2018/6/27.
 */
@RestController
@RequestMapping("/test")
public class TestPoolController {
    @Autowired
    private TaskService taskservice;
    @Autowired
    private UserService userservice;
    @ModelAttribute
    public void myModel(Model model, Principal principal) {
        Map<Integer, String> status = new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("待测试",4);
//        status.put(0,"未领取");
        status.put(1, "处理中");
        status.put(2, "提交待审核");
        status.put(3, "已审核");
        status.put(4, "待测试");
        model.addAttribute("taskstatus", status);
    }

    @RequestMapping(value = {"/", "/{id}"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("taskList", taskservice.findByFinish(TaskStatus.CHECK));
        model.addAttribute("task", id != null ? taskservice.getOne(id) : null);
        return new ModelAndView("/tester/testPool", "taskModel", model);
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String done(@RequestParam(value = "task_id") Long task_id, Principal principal) throws Exception {
        Task task = taskservice.getOne(task_id);
        User u = userservice.getUser(principal.getName());
        Set<User> tester=task.getReceivers();
        tester.add(u);
        task.setReceivers(tester);
        taskservice.done(task, TaskStatus.TEST);
        return "success";
    }
//    @RequestMapping(value = {"/pass"}, method = RequestMethod.GET)
//    public String pass(@RequestParam(value = "task_id") Long task_id,String report) throws Exception {
//        Task task = taskservice.getOne(task_id);
//        TestReport tr=new TestReport();
//        tr.setReport(report);
//        ArrayList<TestReport> al=new ArrayList<>();
//        al.add(tr);
//        task.setTestReport(al);
//        taskservice.done(task, TaskStatus.PASS);
//        return "success";
//    }

}
