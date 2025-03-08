package com.shop28.service.impl;

import com.shop28.entity.CustomUserDetails;
import com.shop28.entity.User;
import com.shop28.service.JwtService;
import com.shop28.util.TypeToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

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
    public String generateToken(User user, TypeToken typeToken) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .subject(user.getUsername())
                .claim("role", getRole(user))
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + getExpirationTime(typeToken)))
                .signWith(getSecretKey(typeToken))
                .compact();
    }

    @Override
    public String verifyTokenAndExtractUserName(String token, TypeToken typeToken) {
        return Jwts.parser()
                .verifyWith(getSecretKey(typeToken))
                .requireIssuer(ISSUER)
                .build()
                .parseClaimsJws(token)
                .getPayload().getSubject();
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
