package com.example.demo.entity;

/**
 * Created by liuyf90 on 2018/9/14.
 */
public enum SystemEnum {
    CENTOS7("centos7",7),CENTOS6("centos6",6),WINDOWSSERVER("WINDOWS_SERVER",1),WINDOWS10("win10",10),WINDOWS7("win7",11);
    // 成员变量
    private String name;
    private int index;
    private SystemEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (SystemEnum c : SystemEnum.values()) {
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
