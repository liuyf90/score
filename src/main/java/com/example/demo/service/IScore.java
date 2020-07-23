package com.example.demo.service;

import com.example.demo.entity.RuleEnum;
import com.example.demo.entity.Score;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.joda.money.Money;

import java.util.List;

/**
 * Created by liuyf on 2018/6/20.
 */
public interface IScore {
    // 计算分数
    void score(User user, RuleEnum ruleEnum, Task task) throws Exception;

    //总分按人查
    Money scoreByUser(User user);

    //该任务中莫人所得的总分
    Money scoreByTaskofUser(Task task, User user);

    List<Score> scoreDetailsTaskofUser(Task task, User user);

    List<Score> scoreDetailsTask(Task task);
}
