package com.diploma.qoldan.service.user;

import com.diploma.qoldan.dto.user.AuthenticationResponseDto;
import com.diploma.qoldan.exception.user.UserAddressNotFoundException;
import com.diploma.qoldan.model.address.Address;
import com.diploma.qoldan.model.organization.Organization;
import com.diploma.qoldan.model.user.Role;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.user.RoleRepo;
import com.diploma.qoldan.repository.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserSimpleService {

    private final UserRepo repo;
    private final RoleRepo roleRepo;

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("");
        return user;
    }

    public Address getUserAddress(User user) throws UserAddressNotFoundException {
        Address address = user.getAddress();
        if (address == null)
            throw new UserAddressNotFoundException("");
        return address;
    }

    public User findUserById(Long userId) {
        User user = repo.findById(userId);
        if (user == null)
            throw new UsernameNotFoundException("");
        return user;
    }

    public Role findRoleByName(String name) throws RoleNotFoundException {
        Role role = roleRepo.findByName(name);
        if (role == null)
            throw new RoleNotFoundException("");
        return role;
    }

    public AuthenticationResponseDto getUserCredentials(User user, String token) {
        List<Role> roles = user.getRoles();
        Organization organization = user.getOrganization();
        return AuthenticationResponseDto.builder()
                .token(token)
                .id(user.getId())
                .username(user.getEmail())
                .userType(roles.get(0).getName().substring(5))
                .organizationId(organization != null ? organization.getId() : null)
                .build();
    }
}
