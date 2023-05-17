package com.diploma.qoldan.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String city;
    private String address;
    private String building;
    private String apartment;
    private String entrance;
    private String details;
}
