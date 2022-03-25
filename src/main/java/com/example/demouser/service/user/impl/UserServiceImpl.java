package com.example.demouser.service.user.impl;

import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.reporitory.user.UserRepository;
import com.example.demouser.service.converter.user.UserConverter;
import com.example.demouser.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserConverter userConverter;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserConverter userConverter,
                           UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserDto userDto) {
        User user = userConverter.convertToEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(UserDto userDto) {
        User updatedUser = userConverter.convertToEntity(userDto);

        return userRepository.save(updatedUser);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void deleteByLogin(String login) {
        userRepository.deleteByLogin(login);
    }
}
