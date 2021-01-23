package com.example.app.model;

import javax.persistence.*;

@Entity
public class RequestState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stateId;

    @Column
    private String stateName;

    public RequestState(int stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public RequestState() {

    }

    public int getStateId() {
        return this.stateId;
    }

    public String getStateName() {
        return this.stateName;
    }
}
