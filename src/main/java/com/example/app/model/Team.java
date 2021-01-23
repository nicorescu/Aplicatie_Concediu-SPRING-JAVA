package com.example.app.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int teamId;
    @Column
    private String teamName;

    @ManyToMany(mappedBy = "teams")
    private Set<Employee> employees;

    public Team(int teamId, String teamName, Set<Employee> employees) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.employees = employees;
    }

    public Team() {

    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }
}
