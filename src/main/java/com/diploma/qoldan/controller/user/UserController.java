package com.diploma.qoldan.controller.user;

import com.diploma.qoldan.dto.order.AddressDto;
import com.diploma.qoldan.dto.user.UserDto;
import com.diploma.qoldan.exception.user.UserAddressNotFoundException;
import com.diploma.qoldan.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-profile")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5001/", methods = {RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
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

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtoList = service.getUsers();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/address")
    public ResponseEntity<AddressDto> getUserAddress(Authentication auth)
            throws UserAddressNotFoundException {
        AddressDto addressDto = service.getUserAddress(auth.getName());
        return ResponseEntity.ok(addressDto);
    }

    @PutMapping("/address")
    public ResponseEntity<String> updateUserAddress(@RequestBody AddressDto addressDto, Authentication auth)
            throws UserAddressNotFoundException {
        service.updateUserAddress(auth.getName(), addressDto);
        return ResponseEntity.ok("Address was successfully updated");
    }
}
