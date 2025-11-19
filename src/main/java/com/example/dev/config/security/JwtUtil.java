package com.example.dev.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes((StandardCharsets.UTF_8)));
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
            return true;
        }catch (SecurityException e){
            System.out.println("Invalid Jwt Signature: " + e.getMessage());
        }catch (MalformedJwtException e){
            System.out.println("Invalid Jwt Signature: " + e.getMessage());
        }catch (ExpiredJwtException e){
            System.out.println("token expired: " + e.getMessage());

        }catch (UnsupportedJwtException e){
            System.out.println("token is unsupported: " + e.getMessage());

        }catch (IllegalArgumentException e){
            System.out.println("String is empty: " + e.getMessage());

        }
        return false;

    }

    public String getUserFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody();
        return claims.getSubject();
    }


}
