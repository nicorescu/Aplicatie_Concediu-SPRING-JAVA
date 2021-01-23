package com.example.app.api;

import com.example.app.model.Employee;
import com.example.app.model.HolidayRequest;
import com.example.app.service.EmployeeService;
import com.example.app.service.HolidayRequestService;
import com.example.app.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class PendingRequestsController {

    private final EmployeeService employeeService;
    private final HolidayRequestService holidayRequestService;
    private Employee loggedInEmployee;

    public PendingRequestsController(EmployeeService employeeService, HolidayRequestService holidayRequestService) {
        this.employeeService = employeeService;
        this.holidayRequestService = holidayRequestService;
    }

    @GetMapping("/pendingRequests")
    public String pendingRequests(Model model) {
        this.loggedInEmployee = employeeService.getLoggedInEmployee();
        Set<HolidayRequest> requests = this.loggedInEmployee.getRequestsToApprove().stream().filter(req -> req.getState().getStateId() == 1).collect(Collectors.toSet());

        model.addAttribute("requests", requests);

        return "viewPendingRequests";
    }

    @PostMapping("/decline/{id}")
    public String declineRequest(@PathVariable("id") long id){
        try{
            HolidayRequest request = holidayRequestService.getRequestById(id);
            request.setState(Utils.declinedState);
            holidayRequestService.addOrUpdateRequest(request);
        }catch (Exception ex){
            return "error";
        }
        return "redirect:/pendingRequests";
    }

    @PostMapping("/approve/{id}")
    public String approveRequest(@PathVariable("id") long id) {
        try {
            HolidayRequest request = holidayRequestService.getRequestById(id);
            request.setNumberOfApprovals(request.getNumberOfApprovals()+1);
            System.out.println(request.getBossesToApprove().size());
            Set<Employee> bossesToApprove = request.getBossesToApprove();
            bossesToApprove.removeIf(emp -> emp.getEmployeeId() == this.loggedInEmployee.getEmployeeId());
            request.setBossesToApprove(bossesToApprove);
            if(request.getNecessaryApprovals() == request.getNumberOfApprovals()) {
                request.setState(Utils.approvedState);
                request.getEmployee().setRemainingDays(request.getRemainingDays());
                holidayRequestService.updateRequests(request.getEmployee(), request.getNumberOfDays());
            }
            System.out.println(request.getBossesToApprove().size());
            holidayRequestService.addOrUpdateRequest(request);
            return "redirect:/pendingRequests";
        } catch (Exception ex) {
            return "error";
        }
    }
}