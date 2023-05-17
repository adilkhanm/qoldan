package com.diploma.qoldan.mapper.address;

import com.diploma.qoldan.dto.order.AddressDto;
import com.diploma.qoldan.model.address.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto mapAddressToDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .address(address.getAddress())
                .building(address.getBuilding())
                .entrance(address.getEntrance())
                .apartment(address.getApartment())
                .details(address.getDetails())
                .build();
    }

    public Address mapDtoToAddress(AddressDto dto) {
        return Address.builder()
                .city(dto.getCity())
                .address(dto.getAddress())
                .building(dto.getBuilding())
                .entrance(dto.getEntrance())
                .apartment(dto.getApartment())
                .details(dto.getDetails())
                .build();
    }
}
