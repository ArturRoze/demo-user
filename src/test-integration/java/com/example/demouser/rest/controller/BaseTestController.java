package com.example.demouser.rest.controller;

import com.example.demouser.config.postgres.PostgresEmbeddedConfig;
import com.example.demouser.config.postgres.UserCreatorConfig;
import com.example.demouser.config.rest.RestTemplateConfig;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {RestTemplateConfig.class, PostgresEmbeddedConfig.class, UserCreatorConfig.class})
public class BaseTestController {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    private int currentPort;

    public TestRestTemplate buildRestTemplate() {
        return new TestRestTemplate(prepareBaseRestTemplateBuilder());
    }

    public TestRestTemplate buildRestTemplateAuthorized(String token) {

        TestRestTemplate testRestTemplate = new TestRestTemplate(prepareBaseRestTemplateBuilder());

        testRestTemplate.getRestTemplate().getInterceptors().add(new HttpHeaderInterceptor(AUTHORIZATION, token));

        return testRestTemplate;
    }

    private RestTemplateBuilder prepareBaseRestTemplateBuilder() {
        String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL + currentPort).build().toUriString();

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        return restTemplateBuilder.rootUri(uri);
    }
}
