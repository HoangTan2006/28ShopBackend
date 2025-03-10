package com.shop28.service.impl;

import com.shop28.entity.Token;
import com.shop28.repository.TokenRepository;
import com.shop28.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Token updateToken(String accessToken, String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken);
        token.setAccessToken(accessToken);
        return tokenRepository.save(token);
    }
}
