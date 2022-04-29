package com.example.demouser.service.security;

import com.example.demouser.model.domain.user.JwtUserDetails;
import com.example.demouser.model.dto.authentication.AuthUserDto;

public interface JwtTokenService {

    String generate(AuthUserDto authUserDto);

    JwtUserDetails parse(String token);

    boolean valid(String token);
}
