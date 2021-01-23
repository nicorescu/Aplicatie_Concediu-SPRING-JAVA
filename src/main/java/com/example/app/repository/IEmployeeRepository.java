package com.example.app.repository;

import com.example.app.model.Employee;
import com.example.app.model.Role;
import com.example.app.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee,Long> {

    Employee findEmployeeByEmail(String email);
    List<Employee> findEmployeesByTeamsContaining(Team team);
    List<Employee> findEmployeesByTeamsIsContainingAndRoleGreaterThan(Team team, Role role);
    List<Employee> findEmployeesByTeamsIsContaining(Team team);
    Set<Employee> findEmployeesByRole(Role role);
}
