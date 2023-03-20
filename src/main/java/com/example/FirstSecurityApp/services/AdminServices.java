package com.example.FirstSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminServices {
    //Аннотация для блокировки конкретного метода, можно добавить еще роли !!!(or hasRole(ROLE_?????))!!!
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminStaff(){
        System.out.println("Only admin here");
    }
}
