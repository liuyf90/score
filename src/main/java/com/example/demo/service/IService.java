package com.example.demo.service;

import com.example.demo.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by liuyf90 on 2018/6/11.
 */
public  interface  IService<E> {
    public Page<E> findSearch(E model);
    public List<E> findAll();
    public List<E> findAll(E model);
}
