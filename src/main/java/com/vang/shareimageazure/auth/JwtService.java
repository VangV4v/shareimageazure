package com.vang.shareimageazure.auth;

import com.vang.shareimageazure.constant.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(username, claims);
    }

    public String extractUsername(String token) {
        Claims claims = getClaims(token);
        return extractToken(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        return extractUsername(token).equals(userDetails.getUsername()) && extractToken(token, Claims::getExpiration).after(new Date());
    }

    private String createToken(String username, Map<String, Object> claims) {
        return Jwts
                .builder()
                .subject(username)
                .claims(claims)
                .signWith(getKey())
                .expiration(new Date(System.currentTimeMillis() * 120000))
                .compact();
    }

    private Claims getClaims(String token) {

        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    private <T> T extractToken(String token, Function<Claims, T> claimReturn) {
        Claims claims = getClaims(token);
        return claimReturn.apply(claims);
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(Common.SECRET));
    }

}