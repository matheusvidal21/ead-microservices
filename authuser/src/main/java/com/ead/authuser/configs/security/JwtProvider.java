package com.ead.authuser.configs.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    @Value("${ead.auth.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwt(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        final String roles = userPrincipal.getAuthorities().stream()
                .map(role -> {
                    return role.getAuthority();
                }).collect(Collectors.joining(","));
        SecretKey secretKey = Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(userPrincipal.getUserId().toString())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getSubjectJwt(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwt(String authToken) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error(this.jwtSecret);
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }



}
