package com.example.demo.service;

import com.example.demo.entity.PageInfo;
import com.example.demo.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by liuyf90 on 2018/6/11.
 */
public  interface  IService<E> {
     Page<E> findSearch(E model,PageInfo pageInfo);
     List<E> findAll();
     List<E> findAll(E model);
}
