package com.example.demo.dao;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by liuyf on 2018/5/6.
 */
public interface TaskRepository extends JpaRepository<Task,Long>,JpaSpecificationExecutor<Task>{
    List<Task> findByFinishAndType(int finish,int type);
}