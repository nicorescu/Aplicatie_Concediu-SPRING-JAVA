package com.example.app.repository;

import com.example.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Integer> {

    Role findRoleByRoleId( int roleId);

    Role findRoleByRoleName(String roleName);
}
