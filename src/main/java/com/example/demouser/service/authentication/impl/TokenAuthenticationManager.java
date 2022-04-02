package com.example.demouser.service.authentication.impl;

import com.example.demouser.model.domain.JwtUserDetails;
import com.example.demouser.service.security.JwtTokenService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    private final JwtTokenService jwtTokenService;

    @Autowired
    public TokenAuthenticationManager(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!(authentication instanceof TokenAuthentication)) {
            throw new IllegalStateException("Not supported");
        }

        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        JwtUserDetails jwtUserDetails = tryParseToken(tokenAuthentication);

        TokenAuthentication fullAuthenticate = createFullAuthenticate(tokenAuthentication.getCredentials(), jwtUserDetails);

        SecurityContextHolder.getContext().setAuthentication(fullAuthenticate);

        return fullAuthenticate;
    }

    private JwtUserDetails tryParseToken(TokenAuthentication tokenAuthentication) {
        JwtUserDetails jwtUserDetails;
        try {
            jwtUserDetails = jwtTokenService.parse(tokenAuthentication.getCredentials());
        } catch (JwtException e) {
            throw new BadCredentialsException("Token not valid");
        }

        return jwtUserDetails;
    }

    private TokenAuthentication createFullAuthenticate(String token, JwtUserDetails jwtUserDetails) {
        return new TokenAuthentication(
                token,
                jwtUserDetails.getAuthorities(),
                true,
                jwtUserDetails);
    }
}
