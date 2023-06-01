package com.diploma.qoldan.service.user;

import com.diploma.qoldan.dto.order.AddressDto;
import com.diploma.qoldan.dto.user.*;
import com.diploma.qoldan.exception.user.UserAddressNotFoundException;
import com.diploma.qoldan.exception.user.UsernameExistsException;
import com.diploma.qoldan.mapper.address.AddressMapper;
import com.diploma.qoldan.mapper.user.UserMapper;
import com.diploma.qoldan.model.address.Address;
import com.diploma.qoldan.model.user.Role;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.user.UserRepo;
import com.diploma.qoldan.service.address.AddressSimpleService;
import com.diploma.qoldan.service.cart.CartSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;

    private final UserMapper mapper;
    private final AddressMapper addressMapper;

    private final UserSimpleService service;
    private final JwtService jwtService;
    private final QoldanUserDetailsService userDetailsService;
    private final CartSimpleService cartService;
    private final AddressSimpleService addressService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponseDto register(RegisterRequestDto requestDto)
            throws UsernameExistsException, RoleNotFoundException {
        return registerWithRole(requestDto, "ROLE_USER");
    }

    public AuthenticationResponseDto registerWithRole(RegisterRequestDto requestDto, String roleValue)
            throws UsernameExistsException, RoleNotFoundException {
        User checkUser = repo.findByEmail(requestDto.getEmail());
        if (checkUser != null)
            throw new UsernameExistsException("");

        Role role = service.findRoleByName(roleValue);

        Address address = addressService.createAddressForNewUser();
        User user = User.builder()
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .address(address)
                .mobile(requestDto.getMobile())
                .roles(List.of(role))
                .build();
        repo.save(user);

        cartService.createCartForNewUser(user);

        String jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        return service.getUserCredentials(user, jwtToken);
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
        return service.getUserCredentials(user, jwtToken);
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
        repo.save(user);
    }

    public AddressDto getUserAddress(String username) throws UserAddressNotFoundException {
        User user = service.findUserByUsername(username);
        Address address = service.getUserAddress(user);
        return addressMapper.mapAddressToDto(address);
    }

    @Transactional
    public void updateUserAddress(String username, AddressDto addressDto) throws UserAddressNotFoundException {
        User user = service.findUserByUsername(username);
        Address address = service.getUserAddress(user);

        address.setCity(addressDto.getCity());
        address.setAddress(addressDto.getAddress());
        address.setBuilding(addressDto.getBuilding());
        address.setEntrance(addressDto.getEntrance());
        address.setApartment(addressDto.getApartment());
        address.setDetails(addressDto.getDetails());
        user.setAddress(address);
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
