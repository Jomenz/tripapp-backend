package com.tripapp.userservice.controller;

import com.tripapp.userservice.dto.UserResponseDTO;
import com.tripapp.userservice.repository.UserRepository;
import com.tripapp.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 🔐 Protected endpoint to fetch the current user's profile.
     * Requires a valid JWT token and injects user info using Spring Security.
     *
     * @param userDetails Injected from the authenticated JWT
     * @return UserResponseDTO or 404 if user not found
     */
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername(); // Extract email from token

        return userRepository.findByEmail(email)
                .map(user -> {
                    UserResponseDTO response = new UserResponseDTO(
                            user.getFirstName(),
                            user.getLastName(),
                            user.getUsername(),
                            user.getEmail()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
