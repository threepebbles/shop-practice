package com.example.practiceShop.domain.auth;

import com.example.practiceShop.util.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    @Value("${jwt.access-token-subject}")
    public String accessTokenSubject;

    @Value("${jwt.refresh-token-subject}")
    public String refreshTokenSubject;

    @Value("${jwt.admin-subject}")
    public String adminSubject;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-in-seconds}")
    private Long accessTokenExpirationInSeconds;

    @Value("${jwt.refresh-token-expiration-in-seconds}")
    private Long refreshTokenExpirationInSeconds;

    /**
     * JWT 검증
     * <p>
     * 1. JWT was incorrectly constructed
     * <p>
     * 2. JWS signature was discovered, but could not be verified
     * <p>
     * 3. expired token
     */
    public void validateJwt(String jwt) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        try {
            jwtParser.parse(jwt);
        } catch (Exception e) {
            throw new IllegalStateException("잘못된 JWT입니다.");
        }
    }

    /**
     * access token 생성
     */
    public String createAccessToken(UUID memberId) {
        Claims claims = Jwts.claims();
        Date expiration = DateUtil.millisToDate(DateUtil.now().getTime() + accessTokenExpirationInSeconds * 1000L);

        claims.setSubject(accessTokenSubject);
        claims.put("id", memberId);
        return generateJwt(claims, expiration);
    }

    /**
     * refresh token 생성
     */
    public String createRefreshToken(UUID memberId) {
        Claims claims = Jwts.claims();
        Date expiration = DateUtil.millisToDate(DateUtil.now().getTime() + refreshTokenExpirationInSeconds * 1000L);

        claims.setSubject(refreshTokenSubject);
        claims.put("id", memberId);
        return generateJwt(claims, expiration);
    }

    public void validateAccessToken(String accessToken) {
        validateJwt(accessToken);
        String sub = extractClaims(accessToken).getSubject();
        if (!sub.equals(accessTokenSubject)) {
            throw new IllegalStateException("access token이 아닙니다.");
        }
    }

    public void validateRefreshToken(String refreshToken) {
        validateJwt(refreshToken);
        String sub = extractClaims(refreshToken).getSubject();
        if (!sub.equals(refreshTokenSubject)) {
            throw new IllegalStateException("refresh token이 아닙니다.");
        }
    }

    public void validateAdmin(String jwt) {
        validateJwt(jwt);
        String sub = extractClaims(jwt).getSubject();
        if (!sub.equals(adminSubject)) {
            throw new IllegalStateException("admin이 아닙니다.");
        }
    }

    /**
     * claim, 만료시간 기반 JWT 생성
     */
    public String generateJwt(Claims claims, Date expiration) {
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(getSecretKey())
                .compact();
    }

    public UUID extractId(String jwt) {
        Object id = extractClaimByKey(jwt, "id");
        return UUID.fromString(id.toString());
    }

    public Object extractClaimByKey(String jwt, String key) {
        validateJwt(jwt);

        Claims claims = extractClaims(jwt);
        if (!claims.containsKey(key)) {
            throw new IllegalStateException("'" + key + "'가 존재하지 않습니다.");
        }
        return claims.get(key);
    }

    /**
     * jwt 내 Claims 추출
     */
    public Claims extractClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
