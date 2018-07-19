package com.example.demo.web;

import com.example.demo.entity.*;
import com.example.demo.service.impl.ScoreService;
import com.example.demo.service.impl.TaskService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

/**
 * Created by liuyf on 2018/5/6.
 */
@RestController
@RequestMapping("/mytasks")
public class MyTasksController {
    @Autowired
    private UserService userservice;
    @Autowired
    private TaskService taskservice;
    @Autowired
    private ScoreService scoreService;
    @ModelAttribute
    public void myModel(Model model,Principal principal){
        Map<Integer,String> status=new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("测试",4),PASS("通过",5),FAIL("失败",6);
        status.put(0,"未领取");
        status.put(1,"处理中");
        status.put(2,"提交待审核");
        status.put(3,"已审核");
        status.put(4,"测试");
        status.put(5,"通过");
        status.put(6,"失败");
        model.addAttribute("taskstatus",status);
    }

    @RequestMapping(value = {"/", "/{id}"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model, Principal principal) {
        User user = new User();
        user.setSts(1);
        user.setUsername(principal.getName());
        User user1 = userservice.findAll(user).get(0);
        double score = this.scoreService.scoreByUser(user1);
        model.addAttribute("myTaskcount", new BigDecimal(score).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        return new ModelAndView("user/myTasks", "taskModel", model);

    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String done(@RequestParam(value = "task_id") Long task_id) throws Exception {
        Task task = taskservice.getOne(task_id);
        taskservice.done(task, TaskStatus.FINISH);
        return "success";
    }

    /**
     * 测试通过
     * @param task_id
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAuthority('ROLE_TEST')")
    @GetMapping(value = "/{task_id}/pass")
    public String pass(@PathVariable(value = "task_id") Long task_id,Principal principal,String report) throws Exception {
        Task task = taskservice.getOne(task_id);
        User u = userservice.getUser(principal.getName());
        TestReport tr=new TestReport();
        tr.setTester(u);
        tr.setReport(report);
        ArrayList<TestReport> tes=new ArrayList<>();
        tes.add(tr);
        task.setTestReport(tes);
        taskservice.done(task, TaskStatus.PASS);
        return "success";
    }

    /**
     * 测试失败
     * @param task_id
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAuthority('ROLE_TEST')")
    @GetMapping(value = "/{task_id}/fail")
    public String fail(@PathVariable(value = "task_id") Long task_id,String report,Principal principal) throws Exception {
        Task task = taskservice.getOne(task_id);
        User u = userservice.getUser(principal.getName());
        TestReport tr=new TestReport();
        tr.setTester(u);
        tr.setReport(report);
        tr.setType(1);
        ArrayList<TestReport> tes=new ArrayList<>();
        tes.add(tr);
        task.setTestReport(tes);
        taskservice.done(task, TaskStatus.FAIL);
        return "success";
    }
    @PreAuthorize("not hasAuthority('ROLE_TEST')")
    @GetMapping(value = "/correct")
    public String correct(@PathParam(value = "task_id") Long task_id, String report, Principal principal) throws Exception {
        Task task = taskservice.getOne(task_id);
        taskservice.done(task, TaskStatus.TEST);
        return "success";
    }


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Page<Task> query(Task task, Model model, Principal principal, com.example.demo.entity.PageInfo<Task> pageInfo) {
        User u = userservice.getUser(principal.getName());
        Page<Task> taskList = taskservice.findSearchForReceiver(u.getId(),task, pageInfo);
        return setTimeOut(taskList,u);
    }
    private Page<Task> setTimeOut(Page<Task> taskList,User user){
        for(Task task:taskList.getContent()){
            Date eDate=task.geteDate();

            task.setScore(this.scoreService.scoreByTaskofUser(task,user));
            if(eDate!=null) {
                boolean bl = eDate.before(new Date());
                if(task.getFinish()<TaskStatus.FINISH.getIndex()) {
                    task.setIstimeOut(bl);
                }
            }
        }
        return taskList;
    }
    @RequestMapping(value = "/getScore", method = RequestMethod.GET)
    public  List<Score> getScore(@RequestParam(value = "task_id") Long task_id, Model model, Principal principal, com.example.demo.entity.PageInfo<Task> pageInfo) {
        User u = userservice.getUser(principal.getName());
        Task task=taskservice.getOne(task_id);
        List<Score> scoreList= scoreService.scoreDetailsTaskofUser(task,u);
        return scoreList;
    }

}