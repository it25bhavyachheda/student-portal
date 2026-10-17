package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    // =========================================================
    // SECURITY FILTER CHAIN
    // =========================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // =================================================
            // AUTHORIZATION
            // =================================================

            .authorizeHttpRequests(auth -> auth

                // -------------------------------------------------
                // PUBLIC PAGES
                // -------------------------------------------------

                .requestMatchers(
                    "/",
                    "/home",
                    "/about",
                    "/courses",
                    "/contact",
                    "/role-selection",
                    "/login",
                    "/register",
                    "/select-login",
                    "/select-register",
                    "/first-time-user",

                    // Static files
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()


                // -------------------------------------------------
                // ADMIN
                // -------------------------------------------------

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")


                // -------------------------------------------------
                // STUDENT
                // -------------------------------------------------

                .requestMatchers("/student/**")
                .hasRole("STUDENT")


                // -------------------------------------------------
                // EDUCATOR
                // -------------------------------------------------

                .requestMatchers("/educator/**")
                .hasRole("EDUCATOR")


                // -------------------------------------------------
                // EVERYTHING ELSE
                // -------------------------------------------------

                .anyRequest()
                .authenticated()
            )


            // =================================================
            // LOGIN
            // =================================================

            .formLogin(form -> form

                // Login page
                .loginPage("/login")

                // Form submission URL
                .loginProcessingUrl("/login")

                // Your login.html uses "email"
                // instead of the default "username"
                .usernameParameter("email")

                // Password field
                .passwordParameter("password")

                // -------------------------------------------------
                // ROLE-BASED SUCCESS HANDLER
                // -------------------------------------------------

                .successHandler(authenticationSuccessHandler())

                .permitAll()
            )


            // =================================================
            // LOGOUT
            // =================================================

            .logout(logout -> logout

                .logoutUrl("/logout")

                .logoutSuccessUrl("/login?logout")

                .permitAll()
            );

        return http.build();
    }


    // =========================================================
    // ROLE-BASED LOGIN REDIRECTION
    // =========================================================

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            String role = authentication
                    .getAuthorities()
                    .stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority())
                    .orElse("");

            // -----------------------------------------------
            // ADMIN
            // -----------------------------------------------

            if (role.equals("ROLE_ADMIN")) {

                response.sendRedirect("/admin/dashboard");

            }

            // -----------------------------------------------
            // STUDENT
            // -----------------------------------------------

            else if (role.equals("ROLE_STUDENT")) {

                response.sendRedirect("/student/dashboard");

            }

            // -----------------------------------------------
            // EDUCATOR
            // -----------------------------------------------

            else if (role.equals("ROLE_EDUCATOR")) {

                response.sendRedirect("/educator/dashboard");

            }

            // -----------------------------------------------
            // UNKNOWN ROLE
            // -----------------------------------------------

            else {

                response.sendRedirect("/role-selection");
            }
        };
    }


    // =========================================================
    // PASSWORD ENCODER
    // =========================================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}