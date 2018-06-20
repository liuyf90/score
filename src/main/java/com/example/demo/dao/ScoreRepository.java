package com.example.demo.dao;

import com.example.demo.entity.Score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by liuyf on 2018/6/20.
 */
public interface ScoreRepository extends JpaRepository<Score,Long>,JpaSpecificationExecutor<Score> {
}
