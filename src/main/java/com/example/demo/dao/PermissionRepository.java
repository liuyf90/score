package com.example.demo.dao;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by liuyf90 on 2018/9/19.
 */
public interface PermissionRepository extends JpaRepository<Permission,Long>,JpaSpecificationExecutor<Permission> {
}
