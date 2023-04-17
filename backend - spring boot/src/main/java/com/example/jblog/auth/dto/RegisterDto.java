package com.example.jblog.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    // @NotBlank(message = "Username is required")
    private String username;
    // @Email(message = "Email is required")
    private String email;
    // @NotBlank(message = "Password is required")
    private String password;
}
