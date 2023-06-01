package com.diploma.qoldan.mapper.user;

import com.diploma.qoldan.dto.user.UserDto;
import com.diploma.qoldan.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();
    }

}
