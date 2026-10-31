
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------- LOGIN PAGE ----------------

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ---------------- REGISTRATION PAGE ----------------

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // ---------------- PROCESS REGISTRATION ----------------

    @PostMapping("/register")
    public String processRegistration(

            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("role") String role,

            Model model) {

        // Remove unnecessary spaces
        name = name.trim();
        email = email.trim().toLowerCase();
        role = role.trim().toUpperCase();

        // Validate name
        if (name.isEmpty()) {
            model.addAttribute("error", "Please enter your full name.");
            return "register";
        }

        // Validate email
        if (email.isEmpty()) {
            model.addAttribute("error", "Please enter your email address.");
            return "register";
        }

        // Validate password
        if (password == null || password.length() < 8) {
            model.addAttribute(
                    "error",
                    "Password must contain at least 8 characters."
            );
            return "register";
        }

        // Confirm password
        if (!password.equals(confirmPassword)) {
            model.addAttribute(
                    "error",
                    "Passwords do not match."
            );
            return "register";
        }

        // Check whether email already exists
        if (userRepository.existsByEmail(email)) {
            model.addAttribute(
                    "error",
                    "This email address is already registered."
            );
            return "register";
        }

        // Validate role
        if (!role.equals("STUDENT")
                && !role.equals("EDUCATOR")
                && !role.equals("ADMIN")) {

            model.addAttribute(
                    "error",
                    "Please select a valid role."
            );
            return "register";
        }

        // Create new user
        User user = new User();

        user.setName(name);
        user.setEmail(email);

        // Encrypt password using configured PasswordEncoder
        user.setPassword(passwordEncoder.encode(password));

        // Store role in Spring Security authority format
        user.setRole("ROLE_" + role);

        // Save user in database
        userRepository.save(user);

        // Display success message on login page
        model.addAttribute(
                "message",
                "Registration successful! Please login with your credentials."
        );

        return "login";
    }
}
