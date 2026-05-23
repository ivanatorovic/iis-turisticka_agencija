package com.example.turisticka_agencija.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/zalbe/**").permitAll()
                        .requestMatchers("/api/pravila-kategorizacije/**").permitAll()
                        .requestMatchers("/api/korisnici/**").permitAll()
                        .requestMatchers("/api/zivotni-ciklus/**").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}