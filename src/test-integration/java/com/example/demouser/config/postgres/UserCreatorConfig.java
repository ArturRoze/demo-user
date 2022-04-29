package com.example.demouser.config.postgres;

import com.example.demouser.model.domain.user.UserRole;
import com.example.demouser.model.dto.authentication.AuthUserDto;
import com.example.demouser.model.dto.user.TestUser;
import com.example.demouser.service.authentication.AuthenticatedUserService;
import com.example.demouser.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import javax.annotation.PostConstruct;

@TestConfiguration
public class UserCreatorConfig {

    public static TestUser admin;

    public static TestUser user;

    private final UserService userService;

    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public UserCreatorConfig(UserService userService,
                             AuthenticatedUserService authenticatedUserService) {
        this.userService = userService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostConstruct
    void initUsers() {
        admin = buildAdmin();
        userService.create(admin);
        admin.setToken(generateToken(admin));

        user = buildUser();
        userService.create(user);
        user.setToken(generateToken(user));
    }

    private String generateToken(TestUser testUser) {
        AuthUserDto authenticate = authenticatedUserService.authenticate(testUser.getLogin(), testUser.getPassword());
        return authenticatedUserService.generateAuthenticationUserToken(authenticate);
    }

    private TestUser buildAdmin() {
        TestUser user = new TestUser();
        user.setLogin("Admin");
        user.setPassword("admin");
        user.setEmail("admin@gmail.com");
        user.setUserRole(UserRole.ADMIN);

        return user;
    }

    private TestUser buildUser() {
        TestUser user = new TestUser();
        user.setLogin("User");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        user.setUserRole(UserRole.USER);

        return user;
    }
}
