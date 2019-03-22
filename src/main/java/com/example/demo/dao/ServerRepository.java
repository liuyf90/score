package com.example.demo.dao;

import com.example.demo.entity.Server;
import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by liuyf90 on 2018/9/12.
 */
public interface ServerRepository extends JpaRepository<Server,Long>,JpaSpecificationExecutor<Server> {
}
