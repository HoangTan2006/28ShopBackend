package com.shop28.service;

import com.shop28.entity.Token;

public interface TokenService {
    Token updateToken(String accessToken, String refreshToken);
}
