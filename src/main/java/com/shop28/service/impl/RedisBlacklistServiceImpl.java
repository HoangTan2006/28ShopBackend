package com.shop28.service.impl;

import com.shop28.service.RedisBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisBlacklistServiceImpl implements RedisBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    @Override
    public void saveTokenToBlacklist(String token, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(token, value, 60, TimeUnit.MINUTES);
    }
}
