package com.diploma.qoldan.mapper;

import com.diploma.qoldan.dto.user.UserDto;
import com.diploma.qoldan.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUserToDto(User user);

}
