package com.example.demouser.reporitory.storage.impl;

import com.example.demouser.reporitory.storage.TokenStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenStorageImpl implements TokenStorage {

    private static final String TOKEN_STORAGE_NAME = "UserTokens";

    private final RedisTemplate<String, ?> redisTemplate;

    private final HashOperations<String, String, List<String>> hashOperations;

    @Autowired
    public TokenStorageImpl(RedisTemplate<String, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void put(String login, String token) {
        List<String> tokens = hashOperations.get(TOKEN_STORAGE_NAME, login);

        if (tokens == null) {
            tokens = new ArrayList<>();
        }
        tokens.add(token);

        hashOperations.put(TOKEN_STORAGE_NAME, login, tokens);
    }

    @Override
    public boolean existsByUserLogin(String login, String token) {
        List<String> tokens = hashOperations.get(TOKEN_STORAGE_NAME, login);

        if (CollectionUtils.isEmpty(tokens)) {
            return false;
        }

        return tokens.stream().anyMatch(value -> value.equals(token));
    }

    @Override
    public List<String> findByLogin(String login) {
        return hashOperations.get(TOKEN_STORAGE_NAME, login);
    }

    @Override
    public void deleteAllByLogin(String login) {
        hashOperations.delete(TOKEN_STORAGE_NAME, login);
    }

    @Override
    public void deleteByLogin(String login, String token) {
        List<String> tokens = hashOperations.get(TOKEN_STORAGE_NAME, login);
        if (!CollectionUtils.isEmpty(tokens)) {
            tokens.removeIf(currentToken -> currentToken.equals(token));
            hashOperations.put(TOKEN_STORAGE_NAME, login, tokens);
        }
    }

}
