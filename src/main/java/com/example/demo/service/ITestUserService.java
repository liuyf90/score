package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskUser;

import java.util.List;

/**
 * Created by liuyf90 on 2018/7/12.
 */
public interface ITestUserService extends IService<TaskUser> {
    List<TaskUser> findByTaskId(long task_id);
}
