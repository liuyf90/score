package com.example.demo.entity;

/**
 * Created by liuyf90 on 2018/6/20.
 * 积分规则
 */
public enum RuleEnum {
    CREATE("创建任务",0.2),PULL("领取任务",0.5),FINISH("办结任务",0.5),ASSIGNING("分派任务",0.2),CHECK("审核任务",0.2),TEST("测试任务",0.2),REJECT("驳回",-0.5),BUG("测试出BUG",-0.5),TIMEOUT("超时",-0.25);
    // 成员变量
    private String rule;
    private double score;
    private RuleEnum(String rule, double score) {
        this.rule = rule;
        this.score = score;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
