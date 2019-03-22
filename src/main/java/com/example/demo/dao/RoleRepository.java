package com.example.demo.dao;

import com.example.demo.entity.Project;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by liuyf90 on 2018/9/20.
 */
public interface RoleRepository extends JpaRepository<Role,Long>,JpaSpecificationExecutor<Role> {
}
