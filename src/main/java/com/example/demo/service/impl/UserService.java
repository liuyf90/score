package com.example.demo.service.impl;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.PageInfo;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.IUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by liuyf90 on 2018/5/6.
 */
@Service
@Transactional
public class UserService implements UserDetailsService, IUserServer {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User getOne(Long id){
        return userRepository.getOne(id);
    }

    @Override
    public Page<User> findSearch(User model, PageInfo pageInfo){
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public List<User> findAll(User model) {
        List<User> result = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (model!=null&&model.getSts() != 0) {
                    list.add(cb.equal(root.get("sts").as(Integer.class), model.getSts()));
                }
                if (model!=null&&model.getUsername() != null && model.getUsername() != "") {
                    list.add(cb.equal(root.get("username").as(String.class), model.getUsername()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        return result;
    }

    @Override
    public List<Role> searchRoles(String username) {
        return (List<Role>) userRepository.findByUsername(username).getAuthorities();
    }
}