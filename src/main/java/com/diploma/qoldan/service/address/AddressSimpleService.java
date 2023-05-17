package com.diploma.qoldan.service.address;

import com.diploma.qoldan.model.address.Address;
import com.diploma.qoldan.repository.address.AddressRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressSimpleService {

    private final AddressRepo repo;

    public Address createAddressForNewUser() {
        Address address = new Address();
        repo.save(address);
        return address;
    }
}
