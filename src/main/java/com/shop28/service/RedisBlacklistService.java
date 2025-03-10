package com.shop28.service;

import java.util.concurrent.TimeUnit;

public interface RedisBlacklistService {
    Boolean isTokenBlacklisted(String token);

    void saveTokenToBlacklist(String token, String value, long timeout, TimeUnit unit);
}
