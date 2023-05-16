package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.user.AuthenticationRequestDto;
import com.diploma.qoldan.dto.user.AuthenticationResponseDto;
import com.diploma.qoldan.dto.user.RegisterRequestDto;
import com.diploma.qoldan.dto.user.UserDto;
import com.diploma.qoldan.exception.user.UsernameExistsException;
import com.diploma.qoldan.mapper.UserMapper;
import com.diploma.qoldan.model.user.Role;
import com.diploma.qoldan.enums.RoleEnum;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.RoleRepo;
import com.diploma.qoldan.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final RoleRepo roleRepo;
    private final UserMapper mapper;

    private final JwtService jwtService;
    private final QoldanUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponseDto register(RegisterRequestDto requestDto) throws UsernameExistsException {

        User checkUser = repo.findByEmail(requestDto.getEmail());
        if (checkUser != null) {
            throw new UsernameExistsException("");
        }

        Role role = roleRepo.findByName(RoleEnum.ROLE_USER.toString());
        if (role == null) {
            role = Role.builder()
                    .name(RoleEnum.ROLE_USER.toString())
                    .build();
            roleRepo.save(role);
        }

        User user = User.builder()
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(List.of(role))
                .build();
        repo.save(user);

        String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
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
        User user = repo.findByEmail(requestDto.getEmail());
        String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public UserDto getAuthenticatedUser(String username) {
        User user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return mapper.mapUserToDto(user);
    }

    @Transactional
    public void editUserProfile(UserDto userDto, String username) {
        User user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
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

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("");
        return user;
    }
}
