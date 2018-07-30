package com.example.demo.dao;

import com.example.demo.entity.TaskUser;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by liuyf90 on 2018/7/12.
 */
public interface TaskUserRepository extends JpaRepository<TaskUser,Long>,JpaSpecificationExecutor<TaskUser> {
    List<TaskUser> findByUserId(Long user_id);
}
