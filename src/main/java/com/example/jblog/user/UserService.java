package com.example.jblog.user;

import com.example.jblog.model.User;
import com.example.jblog.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    public User findByUsernameOrEmail(String usernameOrEmail) {
        return userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No user Found"));
    }
}
