package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Admin",
                    "admin@saraswati.edu.in",
                    "admin1*",
                    "ADMIN"
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Student",
                    "student@saraswati.edu.in",
                    "student1*",
                    "STUDENT"
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Educator",
                    "educator@saraswati.edu.in",
                    "educator1*",
                    "EDUCATOR"
            );
        };
    }

    private void createUserIfNotExists(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String password,
            String role) {

        if (!userRepository.existsByEmail(email)) {

            User user = new User();

            user.setName(name);
            user.setEmail(email);

            // Store BCrypt hash, NOT plain password
            user.setPassword(passwordEncoder.encode(password));

            user.setRole(role);

            userRepository.save(user);

            System.out.println(
                    "Created " + role + " account: " + email
            );
        }
    }
}