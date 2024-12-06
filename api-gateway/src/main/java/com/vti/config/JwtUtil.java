package com.vti.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HexFormat;

public class JwtUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("$jwt.sercet")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    private Key getSignKey(String secretKey) {
        log.info("Key code: {}", new String(HexFormat.of().parseHex(secretKey)));
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public boolean verifyToken(String token) {
        try {
            log.info("Token *********** {}", token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            log.info("ERROR verify token: {}", token, ex);
            return false;
        }
    }
}
