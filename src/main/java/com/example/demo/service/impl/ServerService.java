package com.example.demo.service.impl;

import com.example.demo.dao.ServerRepository;
import com.example.demo.entity.*;
import com.example.demo.service.IServer;
import org.hibernate.mapping.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyf90 on 2018/9/12.
 */
@Service
@Transactional
public class ServerService implements IServer {
    @Autowired
    ServerRepository serverRepository;


    @Override
    public Page<Server> findSearch(Server model, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<Server> findAll() {
        return null;
    }

    @Override
    public List<Server> findAll(Server model) {
        return null;
    }

    public Server getOne(Long id) {
        return serverRepository.findOne(id);
    }

    //    public Page<Server> findSearch(Project project,com.example.demo.entity.PageInfo pageInfo) {
//        Specification<Server> specification = new Specification<Server>() {
//            @Override
//            public Predicate toPredicate(Root<Server> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Join<Server,Project> projectJoin = root.join("project", JoinType.LEFT);
//                List<Predicate> predicates = new ArrayList<>();
//                predicates.add(cb.equal(projectJoin.get("projectId"), project.getProjectId()));
//                Predicate[] p = new Predicate[predicates.size()];
//                return cb.and(predicates.toArray(p));
//            }
//        };
//       return serverRepository.findAll(specification, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(),   new Sort(new Sort.Order(Sort.Direction.DESC,"serverId"))));
//    }
    public Page<Server> findSearch(Project project, com.example.demo.entity.PageInfo pageInfo) {
        Specification<Server> specification = new Specification<Server>() {
            @Override
            public Predicate toPredicate(Root<Server> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join<Server,Project> projectJoin = root.join("project", JoinType.LEFT);
                predicates.add(cb.equal(projectJoin.get("projectId"), project.getProjectId()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        };
        return serverRepository.findAll(specification, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(), new Sort(new Sort.Order(Sort.Direction.DESC, "serverId"))));
    }

    public Server save(Server s){
        return serverRepository.save(s);
    }

    public void edit(){
        serverRepository.flush();
    }
}
