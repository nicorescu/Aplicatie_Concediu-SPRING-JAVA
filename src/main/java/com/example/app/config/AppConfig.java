package com.example.app.config;

import com.example.app.service.EmployeeService;
import com.example.app.service.HolidayRequestService;
import com.example.app.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public EmployeeService userManager() {
        return new EmployeeService();
    }

    @Bean
    public HolidayRequestService holidayRequestManager() {
        return new HolidayRequestService();
    }

    @Bean
    public RoleService roleManager(){
        return new RoleService();
    }
}
