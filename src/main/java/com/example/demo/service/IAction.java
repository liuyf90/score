package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.entity.User;

import java.util.List;


/**
 * Created by liuyf90 on 2018/6/19.
 * 行动接口
 */
public interface IAction {
    long score(User model);//积分计算接口
    Task pull(Task task) throws Exception ;//领取任务
    void done(Task t,TaskStatus taskStatus) throws Exception;//处理任务
    void create(Task task)  throws Exception;//创建任务
}
