package com.example.demouser.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties("jwt")
public class JwtTokenConfig {

    private String secret;

    private Duration expires;

    public JwtTokenConfig() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Duration getExpires() {
        return expires;
    }

    public void setExpires(Duration expires) {
        this.expires = expires;
    }
}
