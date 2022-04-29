package com.example.demouser.rest.controller.auth;

import com.example.demouser.config.postgres.UserCreatorConfig;
import com.example.demouser.model.dto.authentication.AuthUserDto;
import com.example.demouser.rest.controller.BaseTestController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthenticationControllerTest extends BaseTestController {

    @Test
    public void doLogin_shouldReturnBadRequest_whenRequiredParametersAreNotPresent() {
        AuthUserDto expectedBody = new AuthUserDto();
        MultiValueMap<String, String> emptyParams = new LinkedMultiValueMap<>();

        String url = UriComponentsBuilder.fromPath("/api/v1/auth").queryParams(emptyParams).toUriString();

        ResponseEntity<AuthUserDto> response = buildRestTemplate().exchange(url, HttpMethod.POST, null, AuthUserDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getHeaders().get(HttpHeaders.AUTHORIZATION));
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    public void doLogin_shouldReturnOKWithToken_whenRequiredParametersAreCorrect() {
        MultiValueMap<String, String> emptyParams = new LinkedMultiValueMap<>();
        emptyParams.add("username", UserCreatorConfig.admin.getLogin());
        emptyParams.add("password", UserCreatorConfig.admin.getPassword());

        String url = UriComponentsBuilder.fromPath("/api/v1/auth").queryParams(emptyParams).toUriString();

        ResponseEntity<AuthUserDto> response = buildRestTemplate().exchange(url, HttpMethod.POST, null, AuthUserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get(HttpHeaders.AUTHORIZATION));
    }
}