package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.UserDto;
import com.diploma.qoldan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<UserDto> getUserProfile(Authentication auth) {
        UserDto userDto = service.getAuthenticatedUser(auth.getName());
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<String> editUserProfile(@RequestBody UserDto userDto, Authentication auth) {
        service.editUserProfile(userDto, auth.getName());
        return ResponseEntity.ok("User profile was successfully updated");
    }
}
