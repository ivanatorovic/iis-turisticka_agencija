package com.example.turisticka_agencija.dto;

import com.example.turisticka_agencija.model.Role;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String contact;
    private Role role;

    public UserResponse(Long id, String username, String email, String contact, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.contact = contact;
        this.role = role;
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

    public String getContact() {
        return contact;
    }

    public Role getRole() {
        return role;
    }
}