package com.example.demouser.model.domain.authentication;

import com.example.demouser.model.domain.user.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication implements Authentication {

    private final String token;

    private Collection<? extends GrantedAuthority> authorities;

    private boolean isAuthenticated;

    private JwtUserDetails jwtUserDetails;

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public TokenAuthentication(String token,
                               Collection<? extends GrantedAuthority> authorities,
                               boolean isAuthenticated,
                               JwtUserDetails jwtUserDetails) {
        this.token = token;
        this.authorities = authorities;
        this.isAuthenticated = isAuthenticated;
        this.jwtUserDetails = jwtUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return jwtUserDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        if (jwtUserDetails != null)
            return jwtUserDetails.getUsername();
        else
            return null;
    }
}
