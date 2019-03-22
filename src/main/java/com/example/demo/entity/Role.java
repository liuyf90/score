package com.example.demo.entity;

import org.springframework.security.core.GrantedAuthority;


import javax.persistence.*;
import java.util.List;

/**
 * Created by liuyf on 2018/5/3.
 */
@Entity
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String cName;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getAuthority() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcName() {
		return cName;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//级联删除
    @JoinTable(name = "permission_role", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "permissionId"))
	private List<Permission> permissionList;

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
}
