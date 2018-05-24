package com.example.demo.service;

import com.example.demo.entity.Project;
import com.example.demo.entity.User;

import java.util.List;

/**
 * Created by liuyf90 on 2018/5/24.
 */
public interface IProjectService {
    public List<Project> findByUser(User user);
}
