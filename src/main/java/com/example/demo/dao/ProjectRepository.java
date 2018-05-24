package com.example.demo.dao;

import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by liuyf90 on 2018/5/24.
 */
public interface ProjectRepository extends JpaRepository<Project,Long>,JpaSpecificationExecutor<Project> {
    List<Project> findByUser(User user);
}
