package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.AuthenticationRequestDto;
import com.diploma.qoldan.dto.AuthenticationResponseDto;
import com.diploma.qoldan.dto.RegisterRequestDto;
import com.diploma.qoldan.exception.UsernameAlreadyExistsException;
import com.diploma.qoldan.model.Role;
import com.diploma.qoldan.model.User;
import com.diploma.qoldan.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(RegisterRequestDto requestDto) throws UsernameAlreadyExistsException {
        var user = User.builder()
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.USER)
                .build();

        User checkUser = userRepo.findByEmail(requestDto.getEmail()).orElse(null);
        if (checkUser != null) {
            throw new UsernameAlreadyExistsException("");
        }

        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );
        var user = userRepo.findByEmail(requestDto.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
