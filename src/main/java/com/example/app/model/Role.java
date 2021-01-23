package com.example.app.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int roleId;

    @Column
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Role(int roleId, String roleName, Set<Employee> employees) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.employees = employees;
    }

    public Role() {

    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }
}