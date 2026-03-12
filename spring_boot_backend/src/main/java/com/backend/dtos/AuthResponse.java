package com.backend.dtos;

public class AuthResponse {
    private String token;
    private String name;
    private String role;
    private String message;

    public AuthResponse() {}

    public AuthResponse(String token, String name, String role, String message) {
        this.token = token;
        this.name = name;
        this.role = role;
        this.message = message;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}