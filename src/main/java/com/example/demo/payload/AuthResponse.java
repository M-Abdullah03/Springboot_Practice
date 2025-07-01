package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private String email;
    private String message;
    private String token;

    public AuthResponse(String username, String email, String message) {
        this.username = username;
        this.email = email;
        this.message = message;
    }
}