package com.example.demouser.reporitory.storage;

import java.util.List;
import java.util.Set;

public interface TokenStorage {

    void put(String login, String token);

    boolean existsByUserLogin(String login, String token);

    List<String> findByLogin(String login);

    void deleteAllByLogin(String login);

    void deleteByLogin(String login, String token);
}
