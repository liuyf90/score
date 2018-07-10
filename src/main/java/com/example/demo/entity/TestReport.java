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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User tester;

    public Long getTestReportId() {
        return testReportId;
    }

    public void setTestReportId(Long testReportId) {
        this.testReportId = testReportId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getTester() {
        return tester;
    }

    public void setTester(User tester) {
        this.tester = tester;
    }
}
