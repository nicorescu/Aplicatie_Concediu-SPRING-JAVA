package com.example.app.service;

import com.example.app.model.Employee;
import com.example.app.model.Role;
import com.example.app.model.Team;
import com.example.app.repository.IEmployeeRepository;
import com.example.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getLoggedInEmployee() {
        return employeeRepository.findEmployeeByEmail(Utils.getCurrentUsername());
    }

    public List<Employee> getEmployeesByTeam(Team team) {
        return employeeRepository.findEmployeesByTeamsContaining(team);
    }

    public List<Employee> getEmployesPartOfTeam(Team team){
        return employeeRepository.findEmployeesByTeamsIsContaining(team);
    }

    public Set<Employee> getBosses(Role role){
       return  employeeRepository.findEmployeesByRole(role);
    }

    public void addOrUpdateEmployee(Employee employee){
        employeeRepository.save(employee);
    }

}
