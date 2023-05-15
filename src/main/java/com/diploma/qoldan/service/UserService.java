package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.UserDto;
import com.diploma.qoldan.mapper.UserMapper;
import com.diploma.qoldan.model.User;
import com.diploma.qoldan.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public UserDto getAuthenticatedUser(String username) {
        User user = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapper.mapUserToDto(user);
    }

    public void editUserProfile(UserDto userDto, String username) {
        User user = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setMobile(userDto.getMobile());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        repo.save(user);
    }

    public List<UserDto> getUsers() {
        List<User> users = repo.findAll();
        return users
                .stream()
                .map(mapper::mapUserToDto)
                .collect(Collectors.toList());
    }
}
