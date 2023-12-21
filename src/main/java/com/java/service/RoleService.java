package com.java.service;

import com.java.models.Role;
import com.java.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    public RoleRepository roleRepository;

    public Role getRoleByRoleName(String roleName){
        return roleRepository.findRoleByRole_name(roleName);
    }
}
