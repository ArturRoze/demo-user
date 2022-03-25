package com.example.demouser.reporitory.user;

import com.example.demouser.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    void deleteByLogin(String login);
}
