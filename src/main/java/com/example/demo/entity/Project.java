package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
/**
 * Created by liuyf on 2018/5/3.
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    @Column
    private String projectName;
    @JsonIgnore
    @ManyToOne(cascade =  CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id",referencedColumnName="id")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}