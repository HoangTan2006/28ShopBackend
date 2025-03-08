package com.shop28.service;

import com.shop28.entity.User;
import com.shop28.util.TypeToken;

public interface JwtService {
    String generateToken(User user, TypeToken typeToken);

    String verifyTokenAndExtractUserName(String token, TypeToken typeToken);
}
