package com.tripapp.userservice.controller;

import com.tripapp.userservice.dto.ForgotPasswordRequest;
import com.tripapp.userservice.dto.LoginRequest;
import com.tripapp.userservice.dto.RegisterRequest;
import com.tripapp.userservice.dto.ResetPasswordRequest;
import com.tripapp.userservice.entity.User;
import com.tripapp.userservice.service.EmailService;
import com.tripapp.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // ✅ Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String message = userService.registerUser(request);
        return message.equals("User registered successfully!")
                ? ResponseEntity.ok(message)
                : ResponseEntity.badRequest().body(message);
    }

    // ✅ Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String jwt = userService.loginUser(request);
        return jwt != null
                ? ResponseEntity.ok("JWT Token: " + jwt)
                : ResponseEntity.status(401).body("Invalid email or password");
    }

    // ✅ Forgot Password Endpoint
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        String resetLink = "http://localhost:3000/reset-password?email=" + user.getEmail();

        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Reset Your Password",
                    "<p>Click the link to reset your password:</p><a href=\"" + resetLink + "\">Reset Password</a>"
            );
            return ResponseEntity.ok("Reset email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    // ✅ Reset Password Endpoint
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        boolean result = userService.resetPassword(request.getEmail(), request.getNewPassword());

        return result
                ? ResponseEntity.ok("Password updated successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
