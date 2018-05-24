package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;
/**
 * Created by liuyf on 2018/5/3.
 */
public interface IUserServer {
    public List<User> findSearch(User model);
}