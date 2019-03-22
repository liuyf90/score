package com.example.demo.service.impl;

import com.example.demo.dao.ProjectRepository;
import com.example.demo.entity.*;
import com.example.demo.service.IProjectService;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
        Specification<Project> specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        };
        return projectRepository.findAll(specification, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(),   new Sort(new Sort.Order(Sort.Direction.DESC,"projectId"))));
    }

    public Project getOne(Long id){
        return  projectRepository.findOne(id);
    }

    public Project save(Project p){
        return projectRepository.save(p);
    }

    public void edit(){
         projectRepository.flush();
    }




}
