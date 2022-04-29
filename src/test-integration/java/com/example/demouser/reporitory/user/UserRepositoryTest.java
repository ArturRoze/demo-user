package com.example.demouser.reporitory.user;

import com.example.demouser.model.domain.user.UserRole;
import com.example.demouser.model.entity.user.User;
import com.example.demouser.reporitory.BaseTestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class UserRepositoryTest extends BaseTestRepository {

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Test
//    void findByLogin_shouldThrowException_whenInputLoginIsNull() {
//        Executable executable = () -> userRepository.findByLogin(null);
//
//        Assertions.assertThrows(NullPointerException.class, executable);
//    }

    @Test
    void findByLogin_shouldReturnEmpty_whenInputLoginIsNull() {
        Optional<User> user = userRepository.findByLogin(null);

        Assertions.assertFalse(user.isPresent());
    }

    @Test
    void findByLogin_shouldReturnEmpty_whenUserNotPresent() {
        Optional<User> user = userRepository.findByLogin("test");

        Assertions.assertFalse(user.isPresent());
    }

    @Test
    void findByLogin_shouldReturnNotEmpty_whenUserPresent() {
        User savedUser = userRepository.save(buildTestUser());

        Optional<User> user = userRepository.findByLogin(savedUser.getLogin());

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(savedUser.getLogin(), user.get().getLogin());
    }

    @Test
    void findByLoginWithCapabilities() {

    }

    @Test
    void deleteByLogin() {

    }

    private User buildTestUser() {
        User user = new User();

        user.setLogin("test");
        user.setPassword("test");
        user.setEmail("test@gmail.com");
        user.setUserRole(UserRole.USER);

        return user;
    }
}