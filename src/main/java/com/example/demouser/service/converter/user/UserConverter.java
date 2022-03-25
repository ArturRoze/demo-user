package com.example.demouser.service.converter.user;

import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.model.entity.user.User;

public interface UserConverter {

    UserDto convertToDto(User user);

    User convertToEntity(UserDto userDto);
}
