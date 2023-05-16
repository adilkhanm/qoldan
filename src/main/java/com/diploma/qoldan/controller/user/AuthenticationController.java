package com.diploma.qoldan.controller.user;

import com.diploma.qoldan.dto.user.AuthenticationRequestDto;
import com.diploma.qoldan.dto.user.RegisterRequestDto;
import com.diploma.qoldan.exception.UsernameExistsException;
import com.diploma.qoldan.service.UserService;
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

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto requestDto) {
        try {
            return ResponseEntity.ok(service.register(requestDto));
        } catch (UsernameExistsException e) {
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
