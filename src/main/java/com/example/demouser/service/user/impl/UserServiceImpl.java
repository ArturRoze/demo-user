package com.example.demouser.service.user.impl;

import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.reporitory.user.UserRepository;
import com.example.demouser.service.converter.user.UserConverter;
import com.example.demouser.service.user.UserService;
import com.example.demouser.service.validator.user.UserValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserValidateService userValidateService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserConverter userConverter;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserValidateService userValidateService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserConverter userConverter,
                           UserRepository userRepository) {
        this.userValidateService = userValidateService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserDto userDto) {
        userValidateService.validate(userDto);

        User user = userConverter.convertToEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        return user;
    }

    @Override
    public User update(UserDto userDto) {
        User updatedUser = userConverter.convertToEntity(userDto);

        return userRepository.save(updatedUser);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public void deleteByLogin(String login) {
        userRepository.deleteByLogin(login);
    }
}
