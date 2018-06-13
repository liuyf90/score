package com.example.demo.service.impl;

import com.example.demo.dao.ProjectRepository;
import com.example.demo.entity.PageInfo;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.service.IProjectService;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by liuyf90 on 2018/5/24.
 */
@Service
@Transactional
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public List<Project> findByUser(User user) {
        return projectRepository.findByUser(user);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findAll(Project p) {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> findSearch(Project model, PageInfo pageInfo) {
        return null;
    }
}
