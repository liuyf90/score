package com.example.demo.entity;

import javax.persistence.*;

/**
 * Created by liuyf on 2018/7/9.
 */
@Entity
public class TestReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testReportId;
    @Column
    private String report;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId", referencedColumnName = "taskId")
    private Task task;

}
