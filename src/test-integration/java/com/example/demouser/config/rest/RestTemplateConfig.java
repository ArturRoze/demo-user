package com.example.demouser.config.rest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RestTemplateConfig {

    @Bean
    public TestRestTemplate restTemplate() {
        return new TestRestTemplate();
    }

}
