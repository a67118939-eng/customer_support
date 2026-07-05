package com.supportfaq.customersupportfaqaiagent.dto;

public class AuthResponse {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String csrfToken;

    public AuthResponse() {
    }

    public AuthResponse(Long id, String fullName, String email, String role, String csrfToken) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.csrfToken = csrfToken;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getCsrfToken() {
        return csrfToken;
    }
}
