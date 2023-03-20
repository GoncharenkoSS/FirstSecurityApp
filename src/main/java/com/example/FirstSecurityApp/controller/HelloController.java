package com.example.FirstSecurityApp.controller;

import com.example.FirstSecurityApp.security.PersonDetails;
import com.example.FirstSecurityApp.services.AdminServices;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final AdminServices adminServices;

    public HelloController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }

    @GetMapping("/hello")
    public String sayHallo() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage(){
        adminServices.doAdminStaff();
        return "admin";
    }
}
