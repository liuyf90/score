package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import com.github.pagehelper.PageInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by liuyf on 2018/5/6.
 */
public interface ITaskService extends IService<Task> {

    public Task pull(Task task) ;
    public void update2(Task task);
    public List<Task> assignedTasks(long owner_id);
    public Task save(Task task);
    public List<Task> findSearchForOwnerId(long owner_id,Task model);
    public List<Task> findAllByAdmin();
}