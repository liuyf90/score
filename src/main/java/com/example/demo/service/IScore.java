package com.example.demo.service;

import com.example.demo.entity.RuleEnum;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;

/**
 * Created by liuyf on 2018/6/20.
 */
public interface IScore {
   // 计算分数
   void score(User user, RuleEnum ruleEnum,Task task) throws Exception;

   //总分按人查
   double scoreByUser(User user);
   //总分按任务查
   double scoreByTask(Task task);
}
