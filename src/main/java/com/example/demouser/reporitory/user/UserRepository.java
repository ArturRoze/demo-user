package com.example.demouser.reporitory.user;

import com.example.demouser.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.userCapabilities " +
            "WHERE u.login = :login")
    Optional<User> findByLoginWithCapabilities(String login);

    void deleteByLogin(String login);
}
