package com.example.demo.service.impl;

import com.example.demo.dao.TaskRepository;
import com.example.demo.entity.*;
import com.example.demo.service.ITaskService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by liuyf90 on 2018/5/6.
 */
@Service
@Transactional
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<Task> findAll() {
        // return taskRepository.findAll();
        List<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ListJoin<Task, User> userJoin = root.join(root.getModel().getList("users", User.class), JoinType.LEFT);
                Predicate p1 = cb.isNull(userJoin.get("id"));
                return p1;
            }
        });
        return result;
    }

    @Override
    public Page<Task> findSearch(Task model, com.example.demo.entity.PageInfo pageInfo) {
        Assert.notNull(model);
        Specification<Task> specification=new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(model.getBbdate())) {
                    //大于或等于传入时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("bDate").as(Date.class), model.getBbdate()));
                }
                if (!StringUtils.isEmpty(model.getBedate())) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("bDate").as(Date.class), model.getBedate()));
                }
                if( model.getStatus() == -1){
                    predicates.add(cb.notEqual(root.get("finish").as(Integer.class), TaskStatus.CHECK.getIndex()));
                }
                if (!StringUtils.isEmpty(model.getStatus()) && model.getStatus() != -1) {
                    //狀態
                    predicates.add(cb.equal(root.get("finish").as(Integer.class), model.getStatus()));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        };
        return taskRepository.findAll( specification,new PageRequest(pageInfo.getPage()-1, pageInfo.getLimit(),null));
    }

    @Override
    public List<Task> findAll(Task model){
        List<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, Project> projectJoin = root.join("project", JoinType.LEFT);
                Join<Project, User> userJoin = projectJoin.join("user", JoinType.LEFT);

                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(model.getBbdate())) {
                    //大于或等于传入时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("bDate").as(Date.class), model.getBbdate()));
                }
                if (!StringUtils.isEmpty(model.getBedate())) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("bDate").as(Date.class), model.getBedate()));
                }
                if (!StringUtils.isEmpty(model.getStatus()) && model.getStatus() != -1) {
                    //狀態
                    predicates.add(cb.equal(root.get("finish").as(Integer.class), model.getStatus()));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        });
        return result;
    }


    @Override
    public Page<Task> findSearchForOwnerId(long owner_id, Task model,PageInfo pageInfo) {
        Assert.notNull(model);
        Page<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, Project> projectJoin = root.join("project", JoinType.LEFT);
                Join<Project, User> userJoin = projectJoin.join("user", JoinType.LEFT);

                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(userJoin.get("id"), owner_id));
                if (!StringUtils.isEmpty(model.getBbdate())) {
                    //大于或等于传入时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("bDate").as(Date.class), model.getBbdate()));
                }
                if (!StringUtils.isEmpty(model.getBedate())) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("bDate").as(Date.class), model.getBedate()));
                }
                if (!StringUtils.isEmpty(model.getStatus()) && model.getStatus() != -1) {
                    //狀態
                    predicates.add(cb.equal(root.get("finish").as(Integer.class), model.getStatus()));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        },new PageRequest(pageInfo.getPage()-1, pageInfo.getLimit(),null));
        return result;
    }

    @Override
    public List<Task> assignedTasks(long owner_id) {
        List<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, Project> projectJoin = root.join("project", JoinType.LEFT);
                Join<Project, User> userJoin = projectJoin.join("user", JoinType.LEFT);
                Predicate p2 = cb.equal(userJoin.get("id"), owner_id);
//                Predicate p1=cb.equal(root.get("finish").as(Integer.class), 1);//必须是已经提交的
                return cb.and(p2);
            }
        });
        return result;
    }

    public Task getOne(Long id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task pull(Task task) {
        task.setFinish(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public void done(Task task,TaskStatus taskStatus) {
        if(TaskStatus.FINISH==taskStatus) {
            task.setfDate(new Date());
        }
        if(TaskStatus.CHECK==taskStatus) {
            task.setCheckDate(new Date());
        }
        if(TaskStatus.DONE==taskStatus) {
            task.setbDate(new Date());
        }
        task.setFinish(taskStatus);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public long score(User model) {
       return taskRepository.count(new Specification<Task>() {
           @Override
           public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               Join<Task, User> userJoin = root.join("receivers", JoinType.LEFT);
               Predicate p = cb.equal(userJoin.get("id"), model.getId());
               return cb.and(p);
           }
       });

    }
}