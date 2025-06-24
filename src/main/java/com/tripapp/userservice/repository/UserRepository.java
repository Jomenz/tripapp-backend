package com.tripapp.userservice.repository;

import com.tripapp.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔍 Standard query method to find user by email
    Optional<User> findByEmail(String email);

    // 🔍 Optional: case-insensitive email lookup
    Optional<User> findByEmailIgnoreCase(String email);
}
