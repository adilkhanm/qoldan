package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.AuthenticationRequestDto;
import com.diploma.qoldan.dto.AuthenticationResponseDto;
import com.diploma.qoldan.dto.RegisterRequestDto;
import com.diploma.qoldan.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto requestDto) {
        return ResponseEntity.ok(service.register(requestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto requestDto) {
        return ResponseEntity.ok(service.authenticate(requestDto));
    }
}
