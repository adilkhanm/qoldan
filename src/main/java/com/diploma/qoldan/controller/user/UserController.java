package com.diploma.qoldan.controller.user;

import com.diploma.qoldan.dto.user.UserDto;
import com.diploma.qoldan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<?> getUserProfile(Authentication auth) {
        UserDto userDto = service.getAuthenticatedUser(auth.getName());
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<?> editUserProfile(@RequestBody UserDto userDto, Authentication auth) {
        service.editUserProfile(userDto, auth.getName());
        return ResponseEntity.ok("User profile was successfully updated");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<UserDto> userDtoList = service.getUsers();
        return ResponseEntity.ok(userDtoList);
    }
}
