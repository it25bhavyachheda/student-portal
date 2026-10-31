
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/css/**", "/js/**", "/images/**",
                    "/", "/login", "/register", "/h2-console/**"
                ).permitAll()
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/educator/**").hasRole("EDUCATOR")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    String redirectUrl = "/";

                    var authorities = authentication.getAuthorities();

                    if (authorities.stream().anyMatch(
                        a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                        redirectUrl = "/student/examination";
                    } else if (authorities.stream().anyMatch(
                        a -> a.getAuthority().equals("ROLE_EDUCATOR"))) {
                        redirectUrl = "/educator/examination";
                    } else if (authorities.stream().anyMatch(
                        a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        redirectUrl = "/admin/dashboard";
                    }

                    response.sendRedirect(redirectUrl);
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
