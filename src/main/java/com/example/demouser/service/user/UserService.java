package com.example.demouser.service.user;

import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.model.entity.user.User;

public interface UserService {

    User create(UserDto userDto);

    User update(UserDto userDto);

    User findByLogin(String login);

    void deleteByLogin(String login);

}
