package com.diploma.qoldan.mapper;

import com.diploma.qoldan.dto.UserDto;
import com.diploma.qoldan.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUserToDto(User user);

}
