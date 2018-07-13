package com.example.demo.service;

import com.example.demo.dao.TaskRepository;
import com.example.demo.dao.TaskUserRepository;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyf on 2018/6/20.
 * 实现了任务的领取、分派、办结等动作的业务
 */
public abstract class ActionAdapter implements IAction {
    @Autowired
    protected TaskRepository taskRepository;
    @Autowired
    protected TaskUserRepository taskUserRepository;

    @Override
    public Task pull(Task task, TaskUser taskUser,User user) throws Exception{
        task.setFinish(TaskStatus.DONE);
        taskUserRepository.save(taskUser);
        return taskRepository.save(task);
    }



    @Override
    public void done(Task task, TaskStatus taskStatus) throws Exception{
        if (TaskStatus.FINISH == taskStatus) {
            task.setfDate(new Date());
        }
        if (TaskStatus.CHECK == taskStatus) {
            task.setCheckDate(new Date());
        }
        if (TaskStatus.DONE == taskStatus) {
            task.setbDate(new Date());
        }
        task.setFinish(taskStatus);
    }

    @Override
    public  void create(Task task)  throws Exception{
        taskRepository.save(task);
    }

    @Override
    public void pass(Task t, TaskStatus taskStatus) throws Exception {

    }
}
