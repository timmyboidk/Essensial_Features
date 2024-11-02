package com.easyride.user_service;
import lombok.Data;

@Data
public class JwtResponse {

    private String token;
    private String tokenType = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }
}

