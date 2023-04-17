package com.example.jblog.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.example.jblog.auth.dto.AuthenticationResponse;
import com.example.jblog.model.enums.TokenType;
import com.example.jblog.service.JwtService;
import com.example.jblog.service.MailService;
import com.example.jblog.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jblog.auth.dto.LoginDto;
import com.example.jblog.auth.dto.RegisterDto;
import com.example.jblog.filter.GenericException;
import com.example.jblog.model.Email;
import com.example.jblog.model.Token;
import com.example.jblog.model.User;
import com.example.jblog.repository.TokenRepo;
import com.example.jblog.repository.UserRepo;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final MailService mailService;
    private final AuthenticationManager authMgr;
    private final JwtService jwtService;
    private final UserService userService;

    // =========================== REGISTER ===================
    public AuthenticationResponse register(RegisterDto registerDto) {
        AuthenticationResponse response;

        User user = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
        userRepo.save(user);
        String token = generateAndSaveToken(user);
        String body = mailService.buildBody(token);

        generateAndSendEmail(user.getEmail(), body);
        String jwtToken = jwtService.generateToken(user);


        return  AuthenticationResponse.builder().detail(body).token(jwtToken).build();

    }

    private String generateAndSaveToken(User user) {
        String tokenString = UUID.randomUUID().toString();
        int expireInDays = 15;
        Token token = Token.builder()
                .user(user)
                .tokenString(tokenString)
                .tokenType(TokenType.ACCOUNT_ACTIVATION)
                .expireAt(Instant.now().plus(expireInDays, ChronoUnit.DAYS))
                .build();
        token.setTokenString(tokenString);
        token.setUser(user);

        tokenRepo.save(token);
        return tokenString;
    }

    private void generateAndSendEmail(String recipientEmail, String body) {
        Email email =  Email.builder()
                .subject("Activate your jblog Account")
                .recipient(recipientEmail)
                .body(body).build();
        mailService.sendMail(email);
    }


    // =========================== Login ===================
    public AuthenticationResponse login(LoginDto loginDto) {
        authMgr.authenticate(new
                UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        // user is authenticated
        var user = userService.findByUsernameOrEmail(loginDto.getEmail());

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().detail("LogIn Successful").token(jwtToken).build();
    }

    // =========================== Verify with token ===================

    public String verifyAccount(String tokenString) {
        Token token = tokenRepo.findByTokenString(tokenString)
                .orElseThrow(() -> new GenericException("Invalid token"));
        fetchAndEnableUser(token);
        tokenRepo.deleteById(token.getId()); // TODO - ensure to delete unused/unactivated tokens
        return "Account Activated Successfully";
    }

    @Transactional
    private void fetchAndEnableUser(Token token) {
        String userId = token.getUser().getId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new GenericException("User does not exist, please register again"));
        user.setEnabled(true);
        userRepo.save(user);
    }

    // =========================== Request for token again ===================
    public AuthenticationResponse resendActivationTokenByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() ->new UsernameNotFoundException("Account Not registered"));
        var token = tokenRepo.findByUserAndTokenType(user, TokenType.ACCOUNT_ACTIVATION)
                .orElseThrow(() ->new UsernameNotFoundException("Account already activated or not registered. Try logging in or registering"));
;
        String body = mailService.buildBody(token.getTokenString());
        generateAndSendEmail(email, body);

        return AuthenticationResponse.builder().detail(body).build();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userService.findByUsernameOrEmail(currentUserName);
    }

}
