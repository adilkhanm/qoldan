package com.diploma.qoldan.service.user;

import com.diploma.qoldan.exception.user.UserAddressNotFoundException;
import com.diploma.qoldan.model.address.Address;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSimpleService {

    private final UserRepo repo;

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
}
