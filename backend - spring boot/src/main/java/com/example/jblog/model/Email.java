package com.example.jblog.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String subject;
    @jakarta.validation.constraints.Email
    @NotEmpty(message = "Email is required and has to be unique")
    @Column(unique = true)
    private String recipient;
    private String body;
}
