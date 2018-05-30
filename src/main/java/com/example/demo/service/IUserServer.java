package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * Created by liuyf on 2018/5/3.
 */
public interface IUserServer {
    public List<User> findSearch(User model);
    public List<Role> searchRoles(String username);
}