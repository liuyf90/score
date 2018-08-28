package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liuyf on 2018/5/3.
 */
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","receivers"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @NotBlank(message = "任务名不能为空")
    @Column
    private String taskName;//(columnDefinition = "COMMENT '任务名'")
    @Column
    private Date cDate;//(columnDefinition = "COMMENT '任务创建时间'")
    @Column
    private Date bDate;//(columnDefinition = "COMMENT '任务创建时间领取时间'")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient
    private Date bbdate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient
    private Date bedate;
    @Column
    private Date eDate;//(columnDefinition = "COMMENT '任务计划完成时间'")
//    @NotBlank
    @Column
    private Date fDate;//(columnDefinition = "COMMENT '任务实际完成时间'")
    @Column
    private Date checkDate;//(columnDefinition = "COMMENT '检查时间'")
    @Column
    private String remark;//(columnDefinition = "COMMENT '任务描述'")
    @Column(name = "type", columnDefinition = "INT default 2 COMMENT '软件编码'", nullable = false)
    private int type;//任务分类
    @Transient
    private String  typeName;//任务分类
    @Transient
    private List<String> receiversByUser;//该任务的处理人列表(中文名)

    public List getReceiversByUser() {
        return receiversByUser;
    }

    public void setReceiversByUser(List receiversByUser) {
        this.receiversByUser = receiversByUser;
    }

    public String getTypeName() {
        return TypeEnum.getName(type);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)//级联删除
//    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "taskId"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private Set<User> receivers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "taskId",nullable = true)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
    private Set<TaskUser> receivers;

    @Column(name = "finish", columnDefinition = "INT default 0 COMMENT '完成状态'", nullable = false)
    private int finish;
    @Transient
    private int status=-1;
    @Transient
    private Boolean istimeOut=false;//是否超时

    @Transient
    private String  finishName;

    public int getFinish() {
        return finish;
    }


    public String getFinishName() {
        return TaskStatus.getName(this.finish);
    }

    public void setFinishName(String finishName) {
        this.finishName = finishName;
    }

    public void setFinish(TaskStatus finish) {
        this.finish =finish.getIndex();

    }


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
//    @JsonIgnore
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "taskId"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private List<User> users;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    public Set<TaskUser> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<TaskUser> receivers) {
        this.receivers = receivers;
    }

    public Date getfDate() {
        return fDate;
    }

    public void setfDate(Date fDate) {
        this.fDate = fDate;
    }

    public Date getBbdate() {
        return bbdate;
    }

    public void setBbdate(Date bbdate) {
        this.bbdate = bbdate;
    }

    public Date getBedate() {
        return bedate;
    }

    public void setBedate(Date bedate) {
        this.bedate = bedate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getIstimeOut() {
        return istimeOut;
    }

    public void setIstimeOut(Boolean istimeOut) {
        this.istimeOut = istimeOut;
    }
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="taskId")
    private List<Score> scores;

    @Transient
    private double score;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "taskId")
    private List<TestReport> TestReport;

    public List<com.example.demo.entity.TestReport> getTestReport() {
        return TestReport;
    }

    public void setTestReport(List<com.example.demo.entity.TestReport> testReport) {
        TestReport = testReport;
    }
}