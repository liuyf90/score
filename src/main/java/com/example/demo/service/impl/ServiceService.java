package com.example.demo.service.impl;

import com.example.demo.dao.ProcessRepository;
import com.example.demo.entity.PageInfo;

import com.example.demo.entity.Process;
import com.example.demo.entity.Project;
import com.example.demo.entity.Server;
import com.example.demo.service.IProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘宇飞 on 2019-05-31.
 */
@Service
@Transactional
public class ServiceService implements IProcess{
    @Autowired
    ProcessRepository processRepository;

    public Process save(Process s){
        return processRepository.save(s);
    }
    public com.example.demo.entity.Process getOne(Long id) {
        return processRepository.findOne(id);
    }

    @Override
    public Page<java.lang.Process> findSearch(java.lang.Process model, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List<java.lang.Process> findAll(java.lang.Process model) {
        return null;
    }

    public Page<Process> findSearch(Process process, PageInfo pageInfo) {
        Specification<Process> specification = new Specification<Process>() {
            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join<Process,Server> serverJoin = root.join("server", JoinType.LEFT);
                if(process!=null)
                predicates.add(cb.equal(serverJoin.get("serverId"), process.getServerId()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        };
        return processRepository.findAll(specification, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(), new Sort(new Sort.Order(Sort.Direction.DESC, "serviceId"))));
    }

    public Page<Process> findSearchByPid(Process process, PageInfo pageInfo) {
        Specification<Process> specification = new Specification<Process>() {
            @Override
            public Predicate toPredicate(Root<Process> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join<Process,Server> serverJoin = root.join("server", JoinType.LEFT);
                if(process!=null)
                    predicates.add(cb.equal(serverJoin.get("serverId"), process.getServerId()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        };
        return processRepository.findAll(specification, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(), new Sort(new Sort.Order(Sort.Direction.DESC, "serviceId"))));
    }

    @Override
    public List<java.lang.Process> findAll() {
        return null;
    }

    public void edit(Process p){
        processRepository.saveAndFlush(p);
    }

    public  void del(Process p){
        processRepository.delete(p.getServiceId());
    }
}
