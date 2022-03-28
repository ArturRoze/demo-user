package com.example.demouser.rest.controller.user;

import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.service.converter.user.UserConverter;
import com.example.demouser.service.user.UserService;
import com.example.demouser.service.validator.user.UserValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserValidateService userValidateService;

    private final UserService userService;

    private final UserConverter userConverter;

    @Autowired
    public UserController(UserValidateService userValidateService,
                          UserService userService,
                          UserConverter userConverter) {
        this.userValidateService = userValidateService;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@RequestBody UserDto userDto) {

        userValidateService.isValidDto(userDto);

        return userConverter.convertToDto(userService.create(userDto));
    }

    @GetMapping("/user/{login}")
    public UserDto findUser(@PathVariable(name = "login") String login) {

        return userConverter.convertToDto(userService.findByLogin(login));
    }

    @PutMapping("/user")
    public UserDto updateUser(@RequestBody UserDto userDto) {

        return userConverter.convertToDto(userService.update(userDto));
    }

    @DeleteMapping("/user/{login}")
    public void deleteUser(@RequestParam(name = "login") String login) {

        userService.deleteByLogin(login);
    }
}
