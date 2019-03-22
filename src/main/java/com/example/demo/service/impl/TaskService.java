package com.example.demo.service.impl;

import com.example.demo.Tools;
import com.example.demo.dao.ScoreRepository;
import com.example.demo.dao.TaskUserRepository;
import com.example.demo.entity.*;
import com.example.demo.service.ActionAdapter;
import com.example.demo.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Created by liuyf90 on 2018/5/6.
 */
@Service
@Transactional
public class TaskService extends ActionAdapter implements ITaskService {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public List<Task> findAll() {
        // return taskRepository.findAll();
        Sort sortx = new Sort(new Sort.Order(Sort.Direction.DESC, "cDate"));
        List<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                SetJoin<Task, TaskUser> userJoin = root.join(root.getModel().getSet("receivers", TaskUser.class), JoinType.LEFT);
                Predicate p1 = cb.isNull(userJoin.get("id"));

                return p1;
            }
        }, sortx);
        return result;
    }

    /**
     * 修改工作任务，同时修改工时积分
     * @param task
     * @throws Exception
     */
    public void upd(Task task) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double score = Tools.dateDiff(sf.parse(sf.format(task.getbDate())), sf.parse(sf.format(task.geteDate())));
//        s.setSocre(task.getScore());
//        s.setScoreId(scoreService.findByRuleAndTaskIdAndUserId("工时积分",task.getTaskId(),task.getUser().getId()).getScoreId());
        Score tscore = new Score();
        List<Score> scores = task.getScores();
        for (Score ss : scores) {
//            if (ss.getUser() == task.getUser()) {
                if (ss.getRule().equals("工时积分")) {
                    tscore=ss;
//                }
            }
        }
        tscore.setSocre(score);
        scoreRepository.save(tscore);
        taskRepository.saveAndFlush(task);
    }

    @Override
    public Page<Task> findSearch(Task model, com.example.demo.entity.PageInfo pageInfo) {
        Assert.notNull(model);
        Specification<Task> specification = new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                Join<Task, TaskUser> taskUserJoin = root.join("receivers", JoinType.LEFT);
                Join<TaskUser, User> userJoin = taskUserJoin.join("user", JoinType.LEFT);
                if (!StringUtils.isEmpty(model.getBbdate())) {
                    //大于或等于传入时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("bDate").as(Date.class), model.getBbdate()));
                }
                if (!StringUtils.isEmpty(model.getBedate())) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("bDate").as(Date.class), model.getBedate()));
                }
                if (!StringUtils.isEmpty(model.getReceiverId()) && model.getReceiverId() != -1) {
                    //任务办理人
                    predicates.add(cb.equal(userJoin.get("id").as(Long.class), model.getReceiverId()));
                }
                if (!StringUtils.isEmpty(model.getTaskName())) {
                    //任务名称
                    predicates.add(cb.like(root.get("taskName").as(String.class), "%" + model.getTaskName() + "%"));
                }

                if (model.getStatus() == -1) {
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
        return taskRepository.findAll(specification, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(), new Sort(new Sort.Order(Sort.Direction.DESC, "cDate"))));
    }

    @Override
    public List<Task> findAll(Task model) {
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
        }, new Sort(new Sort.Order(Sort.Direction.DESC, "cDate")));
        return result;
    }


    @Override
    public Page<Task> findSearchForOwnerId(long owner_id, Task model, PageInfo pageInfo) {
        Assert.notNull(model);
        Page<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, Project> projectJoin = root.join("project", JoinType.LEFT);
                Join<Project, User> userJoin = projectJoin.join("user", JoinType.LEFT);
                Join<Task, TaskUser> taskUserJoin = root.join("receivers", JoinType.LEFT);
                Join<TaskUser, User> user2Join = taskUserJoin.join("user", JoinType.LEFT);
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
                if (!StringUtils.isEmpty(model.getTaskName())) {
                    //任务名称
                    predicates.add(cb.like(root.get("taskName").as(String.class), "%" + model.getTaskName() + "%"));
                }
                if (!StringUtils.isEmpty(model.getReceiverId()) && model.getReceiverId() != -1) {
                    //任务办理人
                    predicates.add(cb.equal(user2Join.get("id").as(Long.class), model.getReceiverId()));
                }
                if (!StringUtils.isEmpty(model.getStatus()) && model.getStatus() != -1) {
                    //狀態
                    predicates.add(cb.equal(root.get("finish").as(Integer.class), model.getStatus()));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(), new Sort(new Sort.Order(Sort.Direction.DESC, "cDate"))));
        return result;
    }


    public Page<Task> findSearchForReceiver(long receiver_id, Task model, PageInfo pageInfo) {
        Assert.notNull(model);
        Page<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, TaskUser> userJoin = root.join("receivers", JoinType.LEFT);
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(userJoin.get("user"), receiver_id));
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
        }, new PageRequest(pageInfo.getPage() - 1, pageInfo.getLimit(), new Sort(new Sort.Order(Sort.Direction.DESC, "cDate"))));
        return result;
    }


    public Task getOne(Long id) {
        return taskRepository.findOne(id);
    }


//    @Override
//    public Task save(Task task) {
//        return taskRepository.save(task);
//    }

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

    @Override
    public List<Task> searchAssignedTasks(long owner_id) {
        List<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, Project> projectJoin = root.join("project", JoinType.LEFT);
                Join<Project, User> userJoin = projectJoin.join("user", JoinType.LEFT);
                Predicate p2 = cb.equal(userJoin.get("id"), owner_id);
//                Predicate p1=cb.equal(root.get("finish").as(Integer.class), 1);//必须是已经提交的
                return cb.and(p2);
            }
        }, new Sort(new Sort.Order(Sort.Direction.DESC, "cDate")));
        return result;
    }

    /**
     * 复写ActionAdapter,加入积分规则,领取任务积分
     *
     * @param taskuser
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Task pull(Task t, TaskUser taskuser, User user) throws Exception {
        Task task = super.pull(t, taskuser, user);
        taskRepository.flush();
        scoreService.score(user, RuleEnum.PULL, task);
        //工时积分
        scoreService.workTimeScore(user, task);
        return task;
    }

    /**
     * 复写ActionAdapter,加入积分规则
     *
     * @param task
     * @return
     * @throws Exception
     */
    @Override
    public void create(Task task) throws Exception {
        super.create(task);
        scoreService.score(task.getUser(), RuleEnum.CREATE, task);//创建任务
    }

    /**
     * 复写ActionAdapter,加入积分规则
     *
     * @param task
     * @param taskStatus
     */
    @Override
    public void done(Task task, TaskStatus taskStatus) throws Exception {
        super.done(task, taskStatus);
//        taskUserRepository.save(task.getReceivers());
//        taskRepository.save(task);
        task = taskRepository.findOne(task.getTaskId());
        Iterator<TaskUser> it = task.getReceivers().iterator();
        switch (taskStatus.getIndex()) {
            case 3:
                scoreService.score(task.getUser(), RuleEnum.CHECK, task);//审核任务积分
                scoreService.checkTimeoutScore(task.getUser(), task);//审核任务超时扣分
                break;
            case 4:
                //接受测试任务没分
                break;
            case 5:
                //测试通过
                scoreService.score(task.getTestReport().get(0).getTester(), RuleEnum.TEST, task);//测试积分
                break;
            case 6:
                //测试BUG
                scoreService.score(task.getTestReport().get(0).getTester(), RuleEnum.BUG, task);//测试BUG积分
                while (it.hasNext()) {
                    TaskUser taskUser = it.next();
                    if (taskUser.getRoleType() == 0) {
                        scoreService.score(taskUser.getUser(), RuleEnum.REJECT, task);//BU驳回G扣分
                    }
                }


                break;
            case 1:
                scoreService.score(task.getUser(), RuleEnum.ASSIGNING, task);//分派任务积分
                //工时积分
                while (it.hasNext()) {
                    TaskUser taskUser = it.next();
                    if (taskUser.getRoleType() == 0) {
                        scoreService.workTimeScore(taskUser.getUser(), task);
                    }
                }
                break;
            case 2:
                while (it.hasNext()) {
                    TaskUser taskUser = it.next();
                    if (taskUser.getRoleType() == 0) {
                        scoreService.score(taskUser.getUser(), RuleEnum.FINISH, task);//办结任务积分
                        scoreService.workTimeoutScore(taskUser.getUser(), task);//超时扣分
                    }
                }

                break;
        }
    }

    @Override
    public void del(long task_id) {
        this.taskRepository.delete(task_id);
    }


    @Override
    public List<Task> findByFinish(TaskStatus taskStatus) {
        return this.taskRepository.findByFinishAndType(taskStatus.getIndex(), TypeEnum.CODE.getIndex());
    }

    @Override
    public List<Task> searchTaskByUserAndDate(long user_id, Date bdate, Date eDate) {
        List<Task> result = taskRepository.findAll(new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Task, TaskUser> userJoin = root.join("receivers", JoinType.LEFT);
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(userJoin.get("user"), user_id));
                predicates.add(cb.equal(userJoin.get("roleType"), 0));//排除测试任务

                if (!StringUtils.isEmpty(bdate)) {
                    //大于或等于传入时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("bDate").as(Date.class), bdate));
                }
                if (!StringUtils.isEmpty(eDate)) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("bDate").as(Date.class), eDate));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new Sort(new Sort.Order(Sort.Direction.DESC, "cDate")));
        return result;
    }

}