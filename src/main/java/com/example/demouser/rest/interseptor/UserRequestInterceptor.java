package com.example.demouser.rest.interseptor;

import com.example.demouser.model.domain.user.JwtUserDetails;
import com.example.demouser.model.domain.user.UserRole;
import com.example.demouser.model.domain.annotation.Secured;
import com.example.demouser.model.domain.authentication.TokenAuthentication;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasLength;

@Component
public class UserRequestInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = getLogger(UserRequestInterceptor.class);

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserRequestInterceptor(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Secured securedAnnotation = ((HandlerMethod) handler).getMethod().getAnnotation(Secured.class);

        if (securedAnnotation == null) {
            return true;
        }

        UserRole[] userRoles = securedAnnotation.userRoles();

        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        if (!hasLength(requestTokenHeader)) {
            throw new BadCredentialsException("Jwt can not be null");
        }

        Authentication authenticate = authenticationManager.authenticate(new TokenAuthentication(requestTokenHeader));

        JwtUserDetails principal = (JwtUserDetails)authenticate.getPrincipal();

        if (Arrays.stream(userRoles).noneMatch(userRole -> userRole == principal.getRole())) {
            throw new AccessDeniedException("Unsupported role");
        }

        return true;
    }

}
