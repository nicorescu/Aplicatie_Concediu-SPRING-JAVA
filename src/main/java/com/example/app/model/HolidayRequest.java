package com.example.app.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class HolidayRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestId;


    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column
    private String fromDate;

    @Column
    private String toDate;

    @Column
    private int numberOfDays;

    @Column
    private int remainingDays;

    @Column
    private int necessaryApprovals;

    @Column(name = "number_of_approvals", columnDefinition = "int default 0")
    private int numberOfApprovals;

    @ManyToOne()
    private RequestState state;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "request_employee",
            joinColumns = @JoinColumn(name = "requestId", referencedColumnName = "requestId"),
            inverseJoinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "employeeId"))
    private Set<Employee> bossesToApprove;

    public HolidayRequest(long requestId, Employee employee, String fromDate, String toDate, int numberOfDays, int remainingDays, int necessaryApprovals, int numberOfApprovals, RequestState state, Set<Employee> bossesToApprove) {
        this.requestId = requestId;
        this.employee = employee;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfDays = numberOfDays;
        this.remainingDays = remainingDays;
        this.necessaryApprovals = necessaryApprovals;
        this.numberOfApprovals = numberOfApprovals;
        this.state = state;
        this.bossesToApprove = bossesToApprove;
    }

    public HolidayRequest() {

    }

    public long getRequestId() {
        return requestId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public int getNecessaryApprovals() {
        return necessaryApprovals;
    }

    public int getNumberOfApprovals() {
        return numberOfApprovals;
    }

    public RequestState getState() {
        return state;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public void setNecessaryApprovals(int necessaryApprovals) {
        this.necessaryApprovals = necessaryApprovals;
    }

    public void setNumberOfApprovals(int numberOfApprovals) {
        this.numberOfApprovals = numberOfApprovals;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public Set<Employee> getBossesToApprove() {
        return bossesToApprove;
    }

    public void setBossesToApprove(Set<Employee> bossesToApprove) {
        this.bossesToApprove = bossesToApprove;
    }
}
