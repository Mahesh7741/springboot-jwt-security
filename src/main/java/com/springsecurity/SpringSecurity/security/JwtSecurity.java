package com.springsecurity.SpringSecurity.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtSecurity {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*1000))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraUsername(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey().toString())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getSubject();
    }


    public boolean isTokenExpired(String token){
        Date expiration=Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public boolean isTokenVaild(String token,String useUsername){
        return useUsername.equals(extraUsername(token)) && !isTokenExpired(token);
    }
}
