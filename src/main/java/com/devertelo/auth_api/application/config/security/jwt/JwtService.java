package com.devertelo.auth_api.application.config.security.jwt;

import com.devertelo.auth_api.domain.users.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserResponse userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserResponse userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtConfig.getTokenExpirationInMinutes());
    }

    public String generateRefreshToken(
            UserResponse userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, jwtConfig.getRefreshTokenExpirationInMinutes());
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserResponse userDetails,
            Integer expirationInMinutes
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(LocalDateTime.now()
                        .plusMinutes(expirationInMinutes)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .claim("email", userDetails.getEmail())
                .claim("username", userDetails.getUsername())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isInvalidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (!username.equals(userDetails.getUsername())) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
