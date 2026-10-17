package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {

    // =========================
    // ROLE SELECTION PAGE
    // =========================

    @GetMapping("/role-selection")
    public String roleSelection() {
        return "role-selection";
    }


    // =========================
    // SELECT ROLE FOR LOGIN
    // =========================

    @GetMapping("/select-login")
    public String selectLogin(
            @RequestParam("role") String role) {

        return "redirect:/login?role=" + role;
    }


    // =========================
    // SELECT ROLE FOR REGISTER
    // =========================

    @GetMapping("/select-register")
    public String selectRegister(
            @RequestParam("role") String role) {

        return "redirect:/register?role=" + role;
    }
}