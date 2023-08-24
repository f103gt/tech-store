package com.store.validation.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class VerificationToken {
    private Key SECRET_KEY;
    private String token;
    private String email;

    public String getToken() {
        return token;
    }

    public VerificationToken() {
        setSECRET_KEY();
    }

    private void setSECRET_KEY() {
        this.SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(int minutes, String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + (long) minutes * 60 * 1000);
        this.email = email;
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
        this.token = token;
        return token;
    }

    public void verifyToken(String token,String email) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            if(!email.equals(this.email)){
                throw  new RuntimeException("Email mismatch");
            }
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired token", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported token format", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed or tampered token", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Token signature verification failed", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid token argument", e);
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
