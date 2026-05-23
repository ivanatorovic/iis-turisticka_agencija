package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.Role;

public class AuthResponse {

    private String token;
    private Long id;
    private String username;
    private String email;
    private Role role;

    public AuthResponse(String token, Long id, String username, String email, Role role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}