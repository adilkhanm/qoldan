package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.AuthenticationRequestDto;
import com.diploma.qoldan.dto.AuthenticationResponseDto;
import com.diploma.qoldan.dto.RegisterRequestDto;
import com.diploma.qoldan.exception.UsernameAlreadyExistsException;
import com.diploma.qoldan.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto requestDto) {
        try {
            return ResponseEntity.ok(service.register(requestDto));
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with such an email already exists");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            return ResponseEntity.ok(service.authenticate(requestDto));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: username or password");
        }
    }
}
