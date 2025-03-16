package com.shop28.repository;

import com.shop28.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Boolean existsByRefreshToken(String refreshToken);

    Token findByRefreshToken(String refreshToken);

    void deleteById(String refreshToken);
}
