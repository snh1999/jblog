package com.example.jblog.repository;

import java.util.Optional;

import com.example.jblog.model.User;
import com.example.jblog.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jblog.model.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token, String> {
    Optional<Token> findByTokenString(String tokenString);
    Optional<Token> findByUserAndTokenType(User user, TokenType tokenType);

}
