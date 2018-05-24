package com.example.demo.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
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
}
