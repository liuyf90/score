package com.example.demo.entity;

/**
 * Created by liuyf on 2018/6/23.
 */
public enum TypeEnum {
    DEF("定义及规划",0),DOC("文档",1),CODE("软件编码",2),REPAIR("功能完善",3),BUSI("公出",4);
    // 成员变量
    private String name;
    private int index;
    private TypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (TypeEnum c : TypeEnum.values()) {
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
