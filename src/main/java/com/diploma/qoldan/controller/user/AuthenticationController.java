package com.diploma.qoldan.controller.user;

import com.diploma.qoldan.dto.user.AuthenticationRequestDto;
import com.diploma.qoldan.dto.user.AuthenticationResponseDto;
import com.diploma.qoldan.dto.user.RegisterRequestDto;
import com.diploma.qoldan.exception.user.UsernameExistsException;
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
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto requestDto)
            throws UsernameExistsException {
        return ResponseEntity.ok(service.register(requestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto requestDto)
            throws AuthenticationException {
        return ResponseEntity.ok(service.authenticate(requestDto));
    }
}
