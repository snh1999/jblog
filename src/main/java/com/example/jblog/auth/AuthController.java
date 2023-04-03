package com.example.jblog.auth;

import com.example.jblog.auth.dto.AuthenticationResponse;
import com.example.jblog.auth.dto.GetEmailDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jblog.auth.dto.LoginDto;
import com.example.jblog.auth.dto.RegisterDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authsService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authsService.register(registerDto));
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authsService.login(loginDto));
    }

    @GetMapping("{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        return new ResponseEntity<>(authsService.verifyAccount(token), HttpStatus.ACCEPTED);

    }

    @PostMapping("resend_token")
    public ResponseEntity<AuthenticationResponse> resendActivationTokenByEmail(@RequestBody GetEmailDto emailDto){
        return new ResponseEntity<>(authsService.resendActivationTokenByEmail(emailDto.getEmail()), HttpStatus.OK);
    }

}
