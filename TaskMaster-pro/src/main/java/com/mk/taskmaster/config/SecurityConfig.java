package com.mk.taskmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/registration", "registration/saveUser", "/css/**",  "/assets/**" , "/", "/aboutUs" , "contactUs" ).permitAll() // Allow registration and static resources
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin((form) -> form
                .loginPage("/login") // Set your custom login page
                .permitAll() // Allow access to the login page for all users
                .defaultSuccessUrl("/user/userDashboard", true) // Redirect to home after successful login
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
