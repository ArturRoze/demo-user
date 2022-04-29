package com.example.demouser.rest.controller.auth;

import com.example.demouser.model.dto.authentication.AuthUserDto;
import com.example.demouser.service.authentication.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public AuthenticationController(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthUserDto> doLogin(@RequestParam String username,
                                               @RequestParam String password) {

        AuthUserDto authUserDto = authenticatedUserService.authenticate(username, password);

        String authenticationToken = authenticatedUserService.generateAuthenticationUserToken(authUserDto);

        return ResponseEntity.status(200)
                .header(HttpHeaders.AUTHORIZATION, authenticationToken)
                .header(ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                .body(authUserDto);
    }

}
