package com.example.demouser.model.domain.user;

import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

public enum UserRole {

    ADMIN,
    USER;

    public static Set<UserRole> getUserRoles() {
        return stream(values())
                .collect(toSet());
    }
}
