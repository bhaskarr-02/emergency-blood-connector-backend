package com.bloodconnector.dto;

import com.bloodconnector.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTO {

    // ---------------- Register Request ----------------
    public static class RegisterRequest {

        @NotBlank(message = "Full name is required")
        private String fullName;

        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        @NotBlank(message = "Phone is required")
        private String phone;

        private User.Role role;

        public RegisterRequest() {}

        public RegisterRequest(String fullName, String email, String password, String phone, User.Role role) {
            this.fullName = fullName;
            this.email = email;
            this.password = password;
            this.phone = phone;
            this.role = role;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public User.Role getRole() {
            return role;
        }

        public void setRole(User.Role role) {
            this.role = role;
        }
    }


    // ---------------- Login Request ----------------
    public static class LoginRequest {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        public LoginRequest() {}

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    // ---------------- Auth Response ----------------
    public static class AuthResponse {

        private String token;
        private String type;
        private Long userId;
        private String email;
        private String fullName;
        private String role;

        public AuthResponse() {}

        public AuthResponse(String token, String type, Long userId, String email, String fullName, String role) {
            this.token = token;
            this.type = type;
            this.userId = userId;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
        }

        public static AuthResponse of(String token, User user) {
            return new AuthResponse(
                    token,
                    "Bearer",
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getRole().name()
            );
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}