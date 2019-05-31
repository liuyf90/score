package com.example.demo.service.impl;

import com.example.demo.dao.ProcessRepository;
import com.example.demo.entity.PageInfo;

import com.example.demo.entity.Process;
import com.example.demo.entity.Server;
import com.example.demo.service.IProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 刘宇飞 on 2019-05-31.
 */
@Service
@Transactional
public class ServiceService implements IProcess{
    @Autowired
    ProcessRepository processRepository;

    public Process save(Process s){
        return processRepository.save(s);
    }
    public com.example.demo.entity.Process getOne(Long id) {
        return processRepository.findOne(id);
    }
    @Override
    public Page findSearch(Object model, PageInfo pageInfo) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findAll(Object model) {
        return null;
    }
}
