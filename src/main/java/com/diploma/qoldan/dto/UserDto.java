package com.diploma.qoldan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String mobile;

}
