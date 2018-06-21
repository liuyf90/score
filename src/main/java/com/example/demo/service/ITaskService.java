package com.example.demo.service;

import com.example.demo.entity.Task;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by liuyf on 2018/5/6.
 */
public interface ITaskService extends IService<Task>,IAction {
//     Task save(Task task);
     List<Task> searchAssignedTasks(long owner_id) throws Exception; //查询分派任务
     Page<Task> findSearchForOwnerId(long owner_id, Task model, com.example.demo.entity.PageInfo pageInfo) ;
}