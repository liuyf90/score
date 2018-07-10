package com.example.demo.entity;

public enum TaskStatus {
    WAITED("未领取",0),DONE("处理中",1),FINISH("提交待审核",2),CHECK("已审核",3),TEST("测试",4),PASS("通过",4),FAIL("失败",5);
    // 成员变量
    private String name;
    private int index;
    private TaskStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (TaskStatus c : TaskStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}