package com.example.demouser.model.domain.user;

import com.example.demouser.model.domain.capability.UserCapabilityName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class JwtUserDetails extends User {

    private final UserRole role;

    private final Set<UserCapabilityName> capabilities;

    public JwtUserDetails(String username,
                          String password,
                          Collection<? extends GrantedAuthority> authorities,
                          UserRole role,
                          Set<UserCapabilityName> capabilities) {
        super(username, password, authorities);
        this.role = role;
        this.capabilities = capabilities;
    }

    public UserRole getRole() {
        return role;
    }

    public Set<UserCapabilityName> getCapabilities() {
        return capabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JwtUserDetails that = (JwtUserDetails) o;
        return role == that.role &&
                Objects.equals(capabilities, that.capabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role, capabilities);
    }

    @Override
    public String toString() {
        return "JwtUserDetails{" +
                "role=" + role +
                ", capabilities=" + capabilities +
                '}';
    }
}
