package com.example.app.api;

import com.example.app.model.Employee;
import com.example.app.model.HolidayRequest;
import com.example.app.service.EmployeeService;
import com.example.app.service.HolidayRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class PersonalRequestsController {

    private final EmployeeService employeeService;
    private final HolidayRequestService holidayRequestService;
    private Employee loggedInEmployee;

    public PersonalRequestsController(EmployeeService employeeService, HolidayRequestService holidayRequestService) {
        this.employeeService = employeeService;
        this.holidayRequestService = holidayRequestService;
    }

    @GetMapping("/personalRequests")
    public String viewPersonalRequests(Model model) {
        this.loggedInEmployee = employeeService.getLoggedInEmployee();


        List<HolidayRequest> personalRequests = this.holidayRequestService.getRequestsByEmployee(this.loggedInEmployee);

        model.addAttribute("requests", personalRequests);

        return "viewPersonalRequests";
    }

    /*@PostMapping("/cancel/{id}")
    public String cancelRequest(@PathVariable("id") long id){
        HolidayRequest request = holidayRequestService.getRequestById(id);
        holidayRequestService.deleteRequest(request);
        return "redirect:/personalRequests";
    }*/
}
