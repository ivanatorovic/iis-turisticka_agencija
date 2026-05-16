package com.example.turisticka_agencija.dto;

public class UpdateProfileResponse {

    private UserResponse user;
    private String token;

    public UpdateProfileResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}