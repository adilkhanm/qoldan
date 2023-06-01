package com.diploma.qoldan.controller.user;

import com.diploma.qoldan.dto.user.AuthenticationRequestDto;
import com.diploma.qoldan.dto.user.AuthenticationResponseDto;
import com.diploma.qoldan.dto.user.RegisterRequestDto;
import com.diploma.qoldan.exception.user.UsernameExistsException;
import com.diploma.qoldan.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto requestDto)
            throws UsernameExistsException, RoleNotFoundException {
        return ResponseEntity.ok(service.register(requestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto requestDto)
            throws AuthenticationException {
        return ResponseEntity.ok(service.authenticate(requestDto));
    }

    @PostMapping("/register/privileged")
    public ResponseEntity<AuthenticationResponseDto> registerWithRole(@RequestBody RegisterRequestDto requestDto,
                                                                      @RequestParam("role") String role)
            throws UsernameExistsException, RoleNotFoundException {
        return ResponseEntity.ok(service.registerWithRole(requestDto, role));
    }
}
