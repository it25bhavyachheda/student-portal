
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/courses")
    public String courses() {
        return "courses";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/first-time-user")
    public String firstTimeUser() {
        return "first-time-user";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/students")
    public String adminStudents() {
        return "admin/students";
    }

    @GetMapping("/admin/educators")
    public String adminEducators() {
        return "admin/educators";
    }

    @GetMapping("/admin/reports")
    public String adminReports() {
        return "admin/reports";
    }

    @GetMapping("/educator/dashboard")
    public String educatorDashboard() {
        return "educator/dashboard";
    }

    @GetMapping("/educator/submissions")
    public String educatorSubmissions() {
        return "educator/submissions";
    }

    @GetMapping("/educator/revaluation-requests")
    public String educatorRevaluationRequests() {
        return "educator/revaluation-requests";
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "student/dashboard";
    }

    @GetMapping("/student/submissions")
    public String studentSubmissions() {
        return "student/submissions";
    }

    @GetMapping("/student/raise-ticket")
    public String raiseTicket() {
        return "student/raise-ticket";
    }
}
