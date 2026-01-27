package com.pragma.challenge.msvc_users.infrastructure.output.security.service;

import com.pragma.challenge.msvc_users.domain.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    private final  String secret;
    private final Integer expirationTime;
    public JwtService(
            @Value("${jwt.secret_key}") String secret,
            @Value("${jwt.expiration_time}") Integer expirationTime) {
        this.secret = secret;
        this.expirationTime = expirationTime;
    }
    public String generateToken(Map<String, String> claims, String username) {
        log.info("Generating token for user: {}", username);
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + this.expirationTime))
                .signWith(getSignatureKey()).compact();
    }

    private Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = getClaim(token, Claims::getSubject);
        return tokenUsername.equals(username);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().verifyWith((SecretKey) getSignatureKey()).build().parseSignedClaims(token).getPayload();
        } catch (SecurityException e) {
            throw new InvalidTokenException();
        }
    }

}
