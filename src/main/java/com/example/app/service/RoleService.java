package com.example.app.service;

import com.example.app.model.Role;
import com.example.app.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private IRoleRepository roleRepository;

    public Role findRoleById(int id){
        return roleRepository.findRoleByRoleId(id);
    }

    public Role findRoleByName(String name){
        return roleRepository.findRoleByRoleName(name);
    }
}
