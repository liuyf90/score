package com.example.demo.web;

import com.example.demo.dao.TaskRepository;
import com.example.demo.dao.TaskUserRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.*;
import com.example.demo.service.ITaskService;
import com.example.demo.util.DesignationWorkDay;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuyf90 on 2018/7/23.
 */
@RestController
@RequestMapping("/analyze")
public class AnalyzeController extends BaseController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private ITaskService taskService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView init(@ModelAttribute("taskInfo") Task task, Model model, Principal principal) throws Exception {
        List<User> userList = userRepository.findAll();
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            List<Role> roles = (List<Role>) userIterator.next().getAuthorities();
            Iterator<Role> iterator = roles.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getAuthority().equals("ROLE_ADMIN")) {
                    userIterator.remove();
                    break;
                }
            }
        }
        String[] users = new String[userList.size()];
        for (int i = 0; i <= userList.size() - 1; i++) {
            users[i] = userList.get(i).getCname();
        }
        model.addAttribute("users", users);
        calTask(userList, task.getBbdate(), task.getBedate(), model);
        model.addAttribute("fullness", calFullness(userList, task.getBbdate(), task.getBedate()));
        return new ModelAndView("owner/analyze", "taskModel", model);
    }

    @RequestMapping(value = {"/query"}, method = RequestMethod.GET)
    public ModelAndView query(@ModelAttribute("taskInfo") Task task, Model model, Principal principal) throws Exception {
        List<User> userList = userRepository.findAll();
        Iterator<User> userIterator = userList.iterator();
        while (userIterator.hasNext()) {
            List<Role> roles = (List<Role>) userIterator.next().getAuthorities();
            Iterator<Role> iterator = roles.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getAuthority().equals("ROLE_ADMIN")) {
                    userIterator.remove();
                    break;
                }
            }
        }
        String[] users = new String[userList.size()];
        for (int i = 0; i <= userList.size() - 1; i++) {
            users[i] = userList.get(i).getCname();
        }
        model.addAttribute("users", users);
        //任务总量
        calTask(userList, task.getBbdate(), task.getBedate(), model);
        //饱满度
        model.addAttribute("fullness", calFullness(userList, task.getBbdate(), task.getBedate()));
        return new ModelAndView("owner/analyze", "taskModel", model);

    }

    /**
     * 根据userList中的每个人的起止时间范围计算饱满度
     *
     * @param userList 用户列表
     * @param bDate    开始时间
     * @param eDate    结束时间
     * @return
     * @throws ParseException
     */
    private String[] calFullness(List<User> userList, Date bDate, Date eDate) throws Exception {
        String[] fullness = new String[userList.size()];
        HashMap specialDay = DesignationWorkDay.setSpecialDay();
        for (int i = 0; i <= userList.size() - 1; i++) {
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate1 = dateFormat1.parse("2018-05-31");//上线日期
            List<Task> t = taskService.searchTaskByUserAndDate(userList.get(i).getId(), bDate, eDate);
            Iterator<Task> taskIterator = t.iterator();
            double afullness = 0.00;
            int sumBoring = 0;
            while (taskIterator.hasNext()) {
                Task task1 = taskIterator.next();
                if (task1.getbDate() == null) {
                    continue;
                }
                if (task1.geteDate() == null) {
                    continue;
                }
             //   int boring = Util.differentDays(task1.getbDate(), task1.geteDate())+1;
                int boring=DesignationWorkDay.getValue(task1.getbDate(), task1.geteDate(),1,specialDay).size();//去掉节假日与周末
                sumBoring = sumBoring + boring;
            }
            int sumDate=DesignationWorkDay.getValue(bDate == null ? myDate1 : bDate, eDate == null ? new Date() : eDate, 1, specialDay).size();//去掉节假日与周末
           // int sumDate = Util.differentDays(bDate == null ? myDate1 : bDate, eDate == null ? new Date() : eDate) + 1;

            afullness = Double.valueOf(sumBoring) / Double.valueOf(sumDate);
            DecimalFormat df = new DecimalFormat("#.00");
            String str = df.format(afullness);
            fullness[i] = str;
        }
        return fullness;
    }

    /**
     * 计算任务总量
     *
     * @param userList
     * @param bDate
     * @param eDate
     * @param model
     */
    private void calTask(List<User> userList, Date bDate, Date eDate, Model model) {
        int[] taskCount = new int[userList.size()];
        int[] taskFinallyCount = new int[userList.size()];
        for (int i = 0; i <= userList.size() - 1; i++) {
            List<Task> t = taskService.searchTaskByUserAndDate(userList.get(i).getId(), bDate, eDate);
            taskCount[i] = t.size();
            Iterator<Task> taskIterator = t.iterator();
            while (taskIterator.hasNext()) {
                Task task1 = taskIterator.next();
                if ((task1.getFinish() != TaskStatus.PASS.getIndex()) && (task1.getFinish() != TaskStatus.CHECK.getIndex())) {
                    taskIterator.remove();
                }
            }
            taskFinallyCount[i] = t.size();
        }
        model.addAttribute("taskCount", taskCount);
        model.addAttribute("taskFinallyCount", taskFinallyCount);
    }
}
