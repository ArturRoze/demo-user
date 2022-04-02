package com.example.demouser.service.authentication.impl;

import com.example.demouser.exception.PasswordException;
import com.example.demouser.model.dto.authenticate.AuthUserDto;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.reporitory.storage.TokenStorage;
import com.example.demouser.reporitory.user.UserRepository;
import com.example.demouser.service.authentication.AuthenticatedUserService;
import com.example.demouser.service.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    private final TokenStorage tokenStorage;

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticatedUserServiceImpl(TokenStorage tokenStorage,
                                        UserRepository userRepository,
                                        JwtTokenService jwtTokenService,
                                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.tokenStorage = tokenStorage;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AuthUserDto authenticate(String login, String password) {
        User user = userRepository.findByLoginWithCapabilities(login)
                .orElseThrow(() -> new EntityNotFoundException("user not found"));

        if (!isPasswordValid(password, user.getPassword())) {
            throw new PasswordException("Password incorrect");
        }

        return createAuthenticatedUser(user);
    }

    @Override
    public String generateAuthenticationUserToken(AuthUserDto authUserDto) {
        String token = jwtTokenService.generate(authUserDto);
        tokenStorage.put(authUserDto.getLogin(), token);
        return token;
    }

    private boolean isPasswordValid(String rowPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rowPassword, encodedPassword);
    }

    private AuthUserDto createAuthenticatedUser(User user) {
        return new AuthUserDto(user.getLogin(), user.getUserRole(), user.getCapabilityNames());
    }

}
