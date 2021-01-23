package com.example.app.api;

import com.example.app.model.Employee;
import com.example.app.model.HolidayRequest;
import com.example.app.model.Team;
import com.example.app.service.EmployeeService;
import com.example.app.service.HolidayRequestService;
import com.example.app.service.RoleService;
import com.example.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CreateRequestController {

    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final HolidayRequestService holidayRequestService;
    private String errors;
    private Employee loggedInEmploye;

    @Autowired
    public CreateRequestController(EmployeeService employeeService, RoleService roleService, HolidayRequestService holidayRequestService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.holidayRequestService = holidayRequestService;
    }


    @GetMapping("/createRequest")
    public String createRequest(Model model) {
        this.loggedInEmploye = employeeService.getLoggedInEmployee();
        HolidayRequest holidayRequest = new HolidayRequest();
        holidayRequest.setRemainingDays(this.loggedInEmploye.getRemainingDays());
        model.addAttribute("errors", errors);
        model.addAttribute("request", holidayRequest);

        return "createRequest";
    }

    @PostMapping("/createRequest")
    public String generateRequest(HolidayRequest request, Model model) {
        int fromDateYear = LocalDate.parse(request.getFromDate()).getYear();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int toDateYear = LocalDate.parse(request.getToDate()).getYear();
        String role = Utils.getCurrentRole();
        try {
            Date fromDate= new SimpleDateFormat("yyyy-mm-dd").parse(request.getFromDate());
            Date toDate = new SimpleDateFormat("yyyy-mm-dd").parse(request.getToDate());
            System.out.println(fromDate + " " + toDate);
            if(fromDate.after(toDate)){
                this.errors = " 'From date' cannot be before 'To date' ";
                return "redirect:/createRequest";
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return "error";
        }

        if (fromDateYear != toDateYear || fromDateYear != currentYear || toDateYear != currentYear) {
            this.errors = "Cannot make a request for a different year";
            return "redirect:/createRequest";
        } else {

            int remainingDays = this.loggedInEmploye.getRemainingDays();
            int calculatedDays = Utils.calculateDays(request.getFromDate(), request.getToDate());
            if (remainingDays - calculatedDays < 0) {
                this.errors = String.format("You cannot take %d days, you only have %d days available", calculatedDays, remainingDays);
                return "redirect:/createRequest";
            } else {
                int newRemainingDays = remainingDays - calculatedDays;
                HolidayRequest holidayRequest = new HolidayRequest();
                holidayRequest.setFromDate(request.getFromDate());
                holidayRequest.setNumberOfDays(calculatedDays);
                holidayRequest.setRemainingDays(newRemainingDays);
                holidayRequest.setToDate(request.getToDate());
                holidayRequest.setEmployee(this.loggedInEmploye);
                holidayRequest.setState(Utils.pendingState);
                holidayRequest.setBossesToApprove(this.getBossesToApprove());

                if(this.loggedInEmploye.getRole().getRoleId()==4){
                    holidayRequest.setState(Utils.approvedState);
                    holidayRequest.setNecessaryApprovals(0);
                    this.loggedInEmploye.setRemainingDays(this.loggedInEmploye.getRemainingDays()-holidayRequest.getNumberOfDays());
                    this.employeeService.addOrUpdateEmployee(this.loggedInEmploye);
                }
                else if (this.loggedInEmploye.getRole().getRoleId() == 1) {
                    holidayRequest.setNecessaryApprovals(holidayRequest.getBossesToApprove().size());
                } else {
                    holidayRequest.setNecessaryApprovals(1);
                }

                for (Employee emp :
                        holidayRequest.getBossesToApprove()) {
                    System.out.println(emp.getEmail());
                }
                boolean success = holidayRequestService.addOrUpdateRequest(holidayRequest);
                if (!success) {
                    return "error";
                }
                errors = null;
            }
        }
        model.addAttribute("role", role);

        return "index";
    }

    Set<Employee> getBossesToApprove() {
        Set<Employee> bosses = new HashSet<>();

        switch (this.loggedInEmploye.getRole().getRoleId()) {
            case 1:
                for (Team team :
                        this.loggedInEmploye.getTeams()) {
                    bosses.addAll(employeeService.getEmployesPartOfTeam(team));
                }

                bosses = bosses.stream().filter(emp -> emp.getRole().getRoleId() == 2).collect(Collectors.toSet());
                break;
            case 2:
                bosses = employeeService.getBosses(Utils.managerRole);
                bosses.addAll(employeeService.getBosses(Utils.ceoRole));
                break;
            case 3:
                bosses = employeeService.getBosses(Utils.ceoRole);
                break;
        }


        return bosses;
    }

}
