package com.shop28.service.impl;

import com.shop28.entity.CustomUserDetails;
import com.shop28.entity.Token;
import com.shop28.entity.User;
import com.shop28.repository.TokenRepository;
import com.shop28.service.JwtService;
import com.shop28.util.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final TokenRepository tokenRepository;

    @Value("${jwt.expire.accessToken}")
    private Long ACCESS_EXPIRATION;

    @Value("${jwt.expire.refreshToken}")
    private Long REFRESH_EXPIRATION;

    @Value("${jwt.secretKey.accessToken}")
    private String ACCESS_KEY;

    @Value("${jwt.secretKey.refreshToken}")
    private String REFRESH_KEY;

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Override
    public String generateToken(User user, TypeToken typeToken, String tokenId) {

        String token = Jwts.builder()
                .id(tokenId)
                .claim("userId", user.getId())
                .subject(user.getUsername())
                .claim("role", getRole(user))
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + getExpirationTime(typeToken)))
                .signWith(getSecretKey(typeToken))
                .compact();

        //Lưu refresh token vào db
        if (TypeToken.REFRESH.equals(typeToken)) tokenRepository.save(Token.builder()
                        .id(tokenId)
                        .username(user.getUsername())
                        .refreshToken(token)
                .build());
        return token;
    }

    @Override
    public Claims verifyToken(String token, TypeToken typeToken) {
        return Jwts.parser()
                .verifyWith(getSecretKey(typeToken))
                .requireIssuer(ISSUER)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    private SecretKey getSecretKey(TypeToken typeToken) {
        return Keys.hmacShaKeyFor((typeToken == TypeToken.ACCESS) ? ACCESS_KEY.getBytes() : REFRESH_KEY.getBytes());
    }

    private Long getExpirationTime(TypeToken typeToken) {
        return (typeToken == TypeToken.ACCESS) ? ACCESS_EXPIRATION : REFRESH_EXPIRATION;
    }

    private String getRole(CustomUserDetails userDetails) {
        String roles = userDetails.getAuthorities().toString();
        return roles.substring(1, roles.length() - 1);
    }
}
