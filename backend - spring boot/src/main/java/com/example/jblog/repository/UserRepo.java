package com.example.jblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    // Optional<User> findByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmailDuplicate);
}
