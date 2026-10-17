package com.example.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================
    // ROLE SELECTION
    // =========================

    // =========================
    // LOGIN PAGE
    // =========================

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    // =========================
    // REGISTER PAGE
    // =========================

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    // =========================
    // PROCESS REGISTRATION
    // =========================

    @PostMapping("/register")
    public String processRegistration(@RequestParam("name") String name,
            @RequestParam("email") String email, @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("role") String role, Model model) {

        // Check password
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }

        // Check existing email
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email is already registered.");
            return "register";
        }

        // Check valid role
        if (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("EDUCATOR")
                && !role.equalsIgnoreCase("STUDENT")) {

            model.addAttribute("error", "Invalid role selected.");
            return "register";
        }

        // Create user
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toUpperCase());

        // Save user
        userRepository.save(user);

        // Registration successful
        model.addAttribute("message", "Registration successful! Please login.");

        return "login";
    }
}
