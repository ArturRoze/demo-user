package com.example.demouser.service.authentication;

import com.example.demouser.model.dto.authenticate.AuthUserDto;

public interface AuthenticatedUserService {

    AuthUserDto authenticate(String login, String password);

    String generateAuthenticationUserToken(AuthUserDto authUserDto);
}