package com.supportfaq.customersupportfaqaiagent.controller;

import com.supportfaq.customersupportfaqaiagent.dto.ApiMessageResponse;
import com.supportfaq.customersupportfaqaiagent.dto.AuthRequests.LoginRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AuthRequests.RegisterRequest;
import com.supportfaq.customersupportfaqaiagent.dto.AuthResponse;
import com.supportfaq.customersupportfaqaiagent.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest requestBody,
                                 HttpServletRequest request) {
        return authService.register(requestBody, request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest requestBody,
                              HttpServletRequest request) {
        return authService.login(requestBody, request);
    }

    @GetMapping("/me")
    public AuthResponse me(HttpServletRequest request) {
        AuthResponse response = authService.me(request);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated.");
        }
        return response;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ApiMessageResponse logout(HttpServletRequest request) {
        authService.logout(request);
        return new ApiMessageResponse("Logged out successfully");
    }
}
