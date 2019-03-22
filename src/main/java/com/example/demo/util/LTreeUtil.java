package com.example.demo.util;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by liuyf90 on 2018/9/21.
 */
@Service
@Transactional
public class LTreeUtil {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public Set<Permission> getCurrentLTree(Long userId){
        List<Role> roleList= (List<Role>) userRepository.getOne(userId).getAuthorities();
        Set<Permission> permissionSet=new TreeSet<Permission>(new Comparator<Permission>(){
            @Override
            public int compare(Permission arg0, Permission arg1) {
                return arg0.getSeq()-arg1.getSeq();
            }
        });
        for(Role r:roleList){
            permissionSet.addAll(r.getPermissionList());
        }
       return permissionSet;
    }
}
