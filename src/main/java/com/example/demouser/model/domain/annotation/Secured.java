package com.example.demouser.model.domain.annotation;

import com.example.demouser.model.domain.user.UserRole;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface Secured {

    UserRole[] userRoles();
}
