package com.example.app.config;

import com.example.app.model.Employee;
import com.example.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final EmployeeService employeeService;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, EmployeeService employeeService) {
        this.passwordEncoder = passwordEncoder;
        this.employeeService=employeeService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        List<UserDetails> list = new ArrayList<>();

        for(Employee user:
             this.employeeService.getAllEmployees()) {
            UserDetails someUser = User.builder()
                    .username(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(user.getRole().getRoleName())
                    .build();

            list.add(someUser);
        }
        System.out.println(list.size());
        return new InMemoryUserDetailsManager(list);
    }


}
