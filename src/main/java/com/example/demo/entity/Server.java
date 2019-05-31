package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyf90 on 2018/9/11.
 */
@Entity
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serverId;
    @Column
    private String used;//用途
    @NotBlank(message = "IP不能为空")
    @Pattern(regexp = "(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))", message = "IP格式错误")
    @Column
    private String ip;
    @NotNull(message = "端口不能为空")
    @Range(min = 0, max = 65535, message = "端口范围需要在0~65535之间")
    @Column
    private int port;


    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    @Range(min = 0, max = 65535, message = "端口范围需要在0~65535之间")
    @Column
    private Integer export;
    


    @NotBlank(message = "登录名不能为空")
    @Column
    private String userName;
    @NotBlank(message = "密码不能为空")
    @Column
    private String password;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    @Column
    private int system;

    @Transient
    private String systemName;

    public String getSystemName() {
        return SystemEnum.getName(system);
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="server_id")
    private List<Process> services=new ArrayList<Process>();

    public List<Process> getServices() {
        return services;
    }

    public void setServices(List<Process> services) {
        this.services = services;
    }

    @Transient
    private Long projectId;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }





}
