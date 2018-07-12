package com.example.demo.service.impl;

import com.example.demo.dao.TaskUserRepository;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by liuyf90 on 2018/7/12.
 */
@Service
@Transactional
public class TaskUserService {
    @Autowired
    private TaskUserRepository taskUserRepository;

    public TaskUser save(TaskUser taskUser){
       TaskUser taskuser=taskUserRepository.save(taskUser);
       taskUserRepository.flush();
       return taskuser;

    }
}
