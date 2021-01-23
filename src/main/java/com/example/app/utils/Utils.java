package com.example.app.utils;


import com.example.app.model.RequestState;
import com.example.app.model.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static int calculateDays(String fromDate, String toDate){

        try {
            LocalDate date1 = LocalDate.parse(fromDate);
            LocalDate date2 = LocalDate.parse(toDate);
            int daysBetween = (int)ChronoUnit.DAYS.between(date1, date2)+1;
            return daysBetween;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }

    public static String getCurrentRole(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getAuthorities().toArray()[0].toString();
    }

    public static RequestState pendingState = new RequestState(1,"Pending");
    public static RequestState approvedState = new RequestState(2,"Approved");
    public static RequestState declinedState = new RequestState(3,"Declined");
    public static Role employeeRole = new Role(1, null, null);
    public static Role bossRole = new Role(2, null, null);
    public static Role managerRole = new Role(3, null, null);
    public static Role ceoRole = new Role(4, null, null);
}
