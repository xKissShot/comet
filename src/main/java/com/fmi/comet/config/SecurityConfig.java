package com.fmi.comet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/messages/**", "/api/channels/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return username -> {
            if ("admin".equals(username)) {
                return User.builder()
                        .username("admin")
                        .password("{bcrypt}" + passwordEncoder.encode("admin")) // Add {bcrypt} prefix
                        .roles("USER", "ADMIN")
                        .build();
            } else if ("user".equals(username)) {
                return User.builder()
                        .username("user")
                        .password("{bcrypt}" + passwordEncoder.encode("user")) // Add {bcrypt} prefix
                        .roles("USER")
                        .build();
            }
            throw new IllegalArgumentException("User not found");
        };
    }


    @Bean
    public PasswordEncoder securityPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
