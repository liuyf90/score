package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 服务器下可选的服务实例
 * Created by 刘宇飞 on 2019-05-30.
 */
@Entity
@Table(name="Service")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "server_id")
    private Server server;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "p_service_id")
//    public Process parentService;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//级联删除
    @JoinTable(name = "service_service", joinColumns = @JoinColumn(name = "serviceId"),
            inverseJoinColumns = @JoinColumn(name = "p_service_id",referencedColumnName = "serviceId"))
    private List<Process> services=new ArrayList<Process>();


    @Transient
    private Long serverId;

//    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "parentService")
////    @Fetch(FetchMode.SUBSELECT)
//    private List<Process> services=new ArrayList<Process>();


    @NotBlank(message = "名字不能为空")
    @Column
    private String name;


    @NotNull(message = "端口不能为空")
    @Range(min = 0, max = 65535, message = "端口范围需要在0~65535之间")
    @Column
    private int port;


    @Column
    private String remark;

    public void setServices(List<Process> services) {
        this.services = services;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }



    public List<Process> getServices() {
        return services;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }


}
