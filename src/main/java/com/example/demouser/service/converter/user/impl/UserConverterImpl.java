package com.example.demouser.service.converter.user.impl;

import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.service.converter.user.UserConverter;
import org.springframework.stereotype.Service;

import static com.example.demouser.util.ConverterUtil.convert;

@Service
public class UserConverterImpl implements UserConverter {

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();

        convert(userDto::setId, user::getId);
        convert(userDto::setLogin, user::getLogin);
        convert(userDto::setEmail, user::getEmail);
        convert(userDto::setUserRole, user::getUserRole);

        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();

        convert(user::setId, userDto::getId);
        convert(user::setLogin, userDto::getLogin);
        convert(user::setEmail, userDto::getEmail);
        convert(user::setUserRole, userDto::getUserRole);

        return user;
    }
}
