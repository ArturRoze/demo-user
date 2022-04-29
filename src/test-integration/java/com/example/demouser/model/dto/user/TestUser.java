package com.example.demouser.model.dto.user;

import java.util.Objects;

public class TestUser extends UserDto {

    private String token;

    public TestUser() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TestUser testUser = (TestUser) o;
        return Objects.equals(token, testUser.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "token='" + token + '\'' +
                '}';
    }
}
