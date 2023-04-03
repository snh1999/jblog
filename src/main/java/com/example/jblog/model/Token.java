package com.example.jblog.model;

import java.time.Instant;

import com.example.jblog.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"tokenType", "userId"})})
public class Token {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String tokenString;
    @Builder.Default
    private TokenType tokenType = TokenType.ACCOUNT_ACTIVATION;
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;
    private Instant expireAt;
}

