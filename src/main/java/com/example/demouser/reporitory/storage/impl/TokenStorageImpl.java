package com.example.demouser.reporitory.storage.impl;

import com.example.demouser.reporitory.storage.TokenStorage;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenStorageImpl implements TokenStorage {

    private final Map<String, Set<String>> loginToTokens = new ConcurrentHashMap<>();

    @Override
    public void put(String login, String token) {
        loginToTokens.computeIfAbsent(login, value -> new HashSet<>()).add(token);
    }

    @Override
    public Set<String> findByLogin(String login) {
        return new HashSet<>(loginToTokens.get(login));
    }

    @Override
    public void deleteByLogin(String login) {
        loginToTokens.remove(login);
    }
}
