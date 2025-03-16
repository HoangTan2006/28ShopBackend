package com.shop28.service;

import com.shop28.entity.User;
import com.shop28.util.TypeToken;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(User user, TypeToken typeToken, String tokenId);

    Claims verifyToken(String token, TypeToken typeToken);
}
