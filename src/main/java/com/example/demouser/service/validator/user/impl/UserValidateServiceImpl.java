package com.example.demouser.service.validator.user.impl;

import com.example.demouser.exception.EntityNotValidException;
import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.reporitory.user.UserRepository;
import com.example.demouser.service.validator.user.UserValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasLength;

@Service
public class UserValidateServiceImpl implements UserValidateService {

    private final UserRepository userRepository;

    @Autowired
    public UserValidateServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(UserDto userDto) {
        if (!hasLength(userDto.getLogin())) {
            throw new EntityNotValidException("login can not be null or empty");
        }
        if (!hasLength(userDto.getPassword())) {
            throw new EntityNotValidException("password can not be null or empty");
        }
        if (!hasLength(userDto.getEmail())) {
            throw new EntityNotValidException("email can not be null or empty");
        }
        if (userRepository.existsByLogin(userDto.getLogin())) {
            throw new EntityNotValidException("duplicate login");
        }

    }
}
