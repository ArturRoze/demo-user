package com.example.demouser.service.user.impl;

import com.example.demouser.model.domain.user.UserRole;
import com.example.demouser.model.dto.user.UserDto;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.reporitory.user.UserRepository;
import com.example.demouser.service.converter.user.UserConverter;
import com.example.demouser.service.user.UserService;
import com.example.demouser.service.validator.user.UserValidateService;
import com.example.demouser.service.validator.user.impl.UserValidateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidateService userValidateService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void create_shouldReturnCreatedUser_whenInputIsValid() {
        String expectedEncodedPassword = "encodedPassword";
        UserDto userDto = buildTestUserDto(UserRole.USER);
        User convertedUser = buildTestUserEntity(UserRole.USER, null);
        User expectedUser = buildTestUserEntity(UserRole.USER, expectedEncodedPassword);

        doNothing().when(userValidateService).validate(userDto);
        doReturn(expectedEncodedPassword).when(bCryptPasswordEncoder).encode(userDto.getPassword());
        doReturn(convertedUser).when(userConverter).convertToEntity(userDto);

        User actualUser = userService.create(userDto);

        verify(userRepository, times(1)).save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void update() {
    }

    @Test
    void findByLogin() {
    }

    @Test
    void deleteByLogin() {
    }

    private UserDto buildTestUserDto(UserRole userRole) {
        UserDto user = new UserDto();

        user.setLogin("test");
        user.setPassword("test");
        user.setEmail("test@gmail.com");
        user.setUserRole(userRole);

        return user;
    }

    private User buildTestUserEntity(UserRole userRole, String password) {
        User user = new User();

        user.setLogin("test");
        user.setPassword(password);
        user.setEmail("test@gmail.com");
        user.setUserRole(userRole);

        return user;
    }
}