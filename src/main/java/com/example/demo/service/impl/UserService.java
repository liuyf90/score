package com.example.demo.service.impl;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.*;
import com.example.demo.service.IUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
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
    @Autowired
    RoleRepository roleRepository;

    //    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username);
//    }
    public User loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<Role> roles = (List) userRepository.getOne(user.getId()).getAuthorities();
            List<Permission> permissions = new ArrayList<>();
            for (Role r : roles) {
                List<Permission> permissionList = roleRepository.getOne(r.getId()).getPermissionList();
                permissions.addAll(permissionList);
            }
//            List<Permission> permissions = permissionRepository.findByUserId(user.getId());
            List<Role> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    Role r = new Role();
                    r.setName(grantedAuthority.getAuthority());
                    grantedAuthorities.add(r);
                }
            }
            User user1 = new User();
            user1.setAuthorities(grantedAuthorities);
            user1.setUsername(user.getUsername());
            user1.setCname(user.getCname());
            user1.setPassword(user.getPassword());
            return user1;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User getOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Page<User> findSearch(User model, PageInfo pageInfo) {
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
                if (model != null && model.getSts() != 0) {
                    list.add(cb.equal(root.get("sts").as(Integer.class), model.getSts()));
                }
                if (model != null && model.getUsername() != null && model.getUsername() != "") {
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

    @Override
    public List<User> searchAllOwners() {
        List<User> result = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<Role, Project> roleJoin = root.join("authorities", JoinType.LEFT);
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(roleJoin.get("name").as(String.class), "ROLE_OWNER"));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        return result;
    }
}