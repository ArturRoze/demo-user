package com.example.demouser.reporitory.storage;

import java.util.Set;

public interface TokenStorage {

    void put(String login, String token);

    boolean existsByUserLogin(String login, String token);

    Set<String> findByLogin(String login);

    void deleteByLogin(String login);
}
