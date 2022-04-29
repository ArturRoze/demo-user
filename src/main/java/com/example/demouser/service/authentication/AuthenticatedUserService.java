package com.example.demouser.service.authentication;

import com.example.demouser.model.dto.authentication.AuthUserDto;
import com.example.demouser.model.entity.user.User;

public interface AuthenticatedUserService {

    User authenticateEntity(String login, String password);
    
    AuthUserDto authenticate(String login, String password);

    String generateAuthenticationUserToken(AuthUserDto authUserDto);
}