package com.tripapp.userservice.service;

import com.tripapp.userservice.dto.LoginRequest;
import com.tripapp.userservice.dto.RegisterRequest;
import com.tripapp.userservice.entity.User;
import com.tripapp.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // ✅ Print all users on app startup (for debugging)
    @PostConstruct
    public void printUsers() {
        System.out.println("✅ All registered users in the database:");
        userRepository.findAll().forEach(user -> System.out.println("📧 " + user.getEmail()));
    }

    // ✅ Register a new user
    public String registerUser(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return "Email is already registered.";
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match.";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return "User registered successfully!";
    }

    // ✅ Login a user and return JWT token
    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        return jwtService.generateToken(user.getEmail());
    }

    // ✅ Find user by email
    public Optional<User> findByEmail(String email) {
        System.out.println("🔍 Looking for user with email: " + email);
        return userRepository.findByEmail(email);
    }

    // ✅ Reset password
    public boolean resetPassword(String email, String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
