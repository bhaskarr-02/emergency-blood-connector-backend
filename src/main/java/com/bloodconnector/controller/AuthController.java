package com.bloodconnector.controller;

import com.bloodconnector.dto.AuthDTO;
import com.bloodconnector.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow React frontend
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Register new user
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDTO.RegisterRequest request) {

        try {

            AuthDTO.AuthResponse response = authService.register(request);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Login user
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO.LoginRequest request) {

        try {

            AuthDTO.AuthResponse response = authService.login(request);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity
                    .status(401)
                    .body(new ErrorResponse("Invalid email or password"));
        }
    }

    /**
     * Simple error response object
     */
    public static class ErrorResponse {

        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}