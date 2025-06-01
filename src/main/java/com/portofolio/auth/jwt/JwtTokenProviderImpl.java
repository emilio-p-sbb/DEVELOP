package com.portofolio.auth.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.portofolio.auth.enums.TokenType;
import com.portofolio.auth.model.Token;
import com.portofolio.auth.repository.TokenRepository;
import com.portofolio.auth.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider{
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Autowired TokenRepository tokenRepository;
    @Autowired UserRepository userRepository;
    @Override
    public Token generateAccessToken(Map<String, Object> extraClaims, long duration, TemporalUnit durationType, UserDetails user) {
        String username = user.getUsername();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plus(duration, durationType);

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(expiryDate))
                .signWith(decodeSecretKey(jwtSecret), SignatureAlgorithm.HS256)
                .compact();

        return new Token(0L, TokenType.ACCESS, token, expiryDate, false, null);
    }

    @Override
    public Token generateRefreshToken(long duration, TemporalUnit durationType, UserDetails user) {
        String username = user.getUsername();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plus(duration, durationType);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(expiryDate))
                .signWith(decodeSecretKey(jwtSecret), SignatureAlgorithm.HS256)
                .compact();

        return new Token(0L, TokenType.REFRESH, token, expiryDate, false, null);
    }

    @Override
    public boolean validateToken(String tokenValue) {
        if(tokenValue == null)
            return false;
        try {
            Jwts.parserBuilder()
                    .setSigningKey(decodeSecretKey(jwtSecret))
                    .build()
                    .parseClaimsJws(tokenValue);
            return true;
        }catch(JwtException e) {
            return false;
        }
    }
    @Override
    public String getUsernameFromToken(String tokenValue) {
        return extractClaim(tokenValue, Claims::getSubject);
    }
    @Override
    public LocalDateTime getExpiryDateFromToken(String tokenValue) {
        return toLocalDateTime(extractClaim(tokenValue, Claims::getExpiration));
    }
    private Key decodeSecretKey(String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(decodeSecretKey(jwtSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Date toDate(LocalDateTime localDateTime) {
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        return Date.from(localDateTime.toInstant(zoneOffset));
    }
    private LocalDateTime toLocalDateTime(Date date) {
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        return date.toInstant().atOffset(zoneOffset).toLocalDateTime();
    }
}