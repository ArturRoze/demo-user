package com.example.demouser.model.dto.authenticate;

import com.example.demouser.model.domain.capability.UserCapabilityName;
import com.example.demouser.model.domain.user.UserRole;

import java.util.Objects;
import java.util.Set;

public class AuthUserDto {

    private String login;

    private UserRole role;

    private Set<UserCapabilityName> capabilities;

    public AuthUserDto() {
    }

    public AuthUserDto(String login, UserRole role, Set<UserCapabilityName> capabilities) {
        this.login = login;
        this.role = role;
        this.capabilities = capabilities;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<UserCapabilityName> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<UserCapabilityName> capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUserDto that = (AuthUserDto) o;
        return Objects.equals(login, that.login) &&
                role == that.role &&
                Objects.equals(capabilities, that.capabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, role, capabilities);
    }

    @Override
    public String toString() {
        return "AuthUserDto{" +
                "login='" + login + '\'' +
                ", role=" + role +
                ", capabilities=" + capabilities +
                '}';
    }
}
