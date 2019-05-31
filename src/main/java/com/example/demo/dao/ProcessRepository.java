package com.example.demo.dao;



import com.example.demo.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 刘宇飞 on 2019-05-31.
 */
public interface ProcessRepository extends JpaRepository<Process,Long>, JpaSpecificationExecutor<Process> {
}
