package com.example.demo.web;

import com.example.demo.entity.*;
import com.example.demo.service.impl.ProjectService;
import com.example.demo.service.impl.ScoreService;
import com.example.demo.service.impl.TaskService;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.security.Principal;
import java.util.*;

/**
 * Created by liuyf90 on 2018/5/20.
 */
@RestController
@RequestMapping("/check")
public class CheckController extends BaseController{
    @Autowired
    private UserService userservice;
    @Autowired
    private TaskService taskservice;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ScoreService scoreService;

    @ModelAttribute
    public void myModel(Model model, Principal principal) {
        Map<Integer, String> status = new HashMap<>();
        //WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("测试",4),PASS("通过",5),FAIL("失败",6);
        status.put(0, "未领取");
        status.put(1, "处理中");
        status.put(2, "提交待审核");
        status.put(3, "已审核");
        status.put(4, "测试");
        status.put(5, "通过");
        status.put(6, "失败");
        model.addAttribute("taskstatus", status);
        model.addAttribute("ROLE_ADMIN", false);
        User u = userservice.getUser(principal.getName());
        List<Role> roles = userservice.searchRoles(u.getUsername());
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getAuthority().equals("ROLE_ADMIN")) {
                model.addAttribute("ROLE_ADMIN", true);
            }
        }
    }

    @RequestMapping(value = {"/", "/{id}"}, method = RequestMethod.GET)
    public ModelAndView init(@PathVariable(value = "id", required = false) Long id, Model model, Principal principal) {
        model.addAttribute("task", id != null ? taskservice.getOne(id) : null);
        model.addAttribute("users", userservice.findAll());
        return new ModelAndView("owner/checkTasks", "taskModel", model);
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String done(@RequestParam(value = "task_id") Long task_id) throws Exception {
        Task task = taskservice.getOne(task_id);
        taskservice.done(task, TaskStatus.CHECK);
        return "success";
    }
    @RequestMapping(value = "/failback", method = RequestMethod.GET)
    public String failback(@RequestParam(value = "task_id") Long task_id) throws Exception {
        Task task = taskservice.getOne(task_id);
        taskservice.done(task, TaskStatus.DONE);
        return "success";
    }

    @RequestMapping(value = "/assigned", method = RequestMethod.GET)
    public String assigned(@RequestParam(value = "user_id") Long user_id, @RequestParam(value = "task_id") Long task_id, @RequestParam(value = "eDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date eDate,@RequestParam(value = "bDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date bDate) throws Exception {
        Task task = taskservice.getOne(task_id);
        User user = userservice.getOne(user_id);
        Set<TaskUser> taskUsers = new HashSet<>();
        task.setbDate(bDate);
        task.seteDate(eDate);
        TaskUser taskUser = new TaskUser();
        taskUser.setUser(user);
        taskUser.setTask(task);
        taskUser.setRoleType(0);
        taskUsers.add(taskUser);
        task.setReceivers(taskUsers);
        taskservice.done(task, TaskStatus.DONE);
        return "success";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Page<Task> query(Task task, Model model, Principal principal, com.example.demo.entity.PageInfo<Task> pageInfo) {
        User user = userservice.getUser(principal.getName());
        model.addAttribute("task", null);
        User u = userservice.getUser(principal.getName());
        List<Role> roles = userservice.searchRoles(u.getUsername());
        Page<Task> taskList = null;
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getAuthority().equals("ROLE_ADMIN")) {
                taskList = taskservice.findSearch(task, pageInfo);
            }
        }
        if (taskList == null || taskList.getSize() == 0) {
            taskList = taskservice.findSearchForOwnerId(user.getId(), task, pageInfo);
        }
        return setTimeOut(taskList);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public String del(@RequestParam(value = "task_id") Long task_id) {
        taskservice.del(task_id);
        return "success";
    }

    private Page<Task> setTimeOut(Page<Task> taskList) {
        for (Task task : taskList.getContent()) {
            //写入任务的处理人中文名
            Iterator<TaskUser> it = task.getReceivers().iterator();
            List<String> list = new ArrayList<>();
            while (it.hasNext()) {
                list.add(it.next().getUser().getCname());
            }
            task.setReceiversByUser(list);
            Date eDate = task.geteDate();
            if (eDate != null) {
                boolean bl = eDate.before(new Date());
                if (task.getFinish() < TaskStatus.CHECK.getIndex()) {
                    task.setIstimeOut(bl);
                }
            }
        }
        return taskList;
    }


    @RequestMapping(value = "/getScore", method = RequestMethod.GET)
    public List<Score> getScore(@RequestParam(value = "task_id") Long task_id, Model model, Principal principal, com.example.demo.entity.PageInfo<Task> pageInfo) {
        Task task = taskservice.getOne(task_id);
        List<Score> scoreList = scoreService.scoreDetailsTask(task);
        return scoreList;
    }


}
