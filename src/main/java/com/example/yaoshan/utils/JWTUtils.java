package com.example.yaoshan.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类，用于生成和解析token
 */
@Component
public class JWTUtils {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expire}")
    private long expire;
    
    /**
     * 获取签名密钥
     */
    private String getSigningKey() {
        return secret;
    }
    
    /**
     * 生成token
     */
    public String generateToken(String openid) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        
        return Jwts.builder()
                .setSubject(openid)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }
    
    /**
     * 解析token
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 判断token是否过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
    
    /**
     * 从token中获取openid
     */
    public String getOpenidFromToken(String token) {
        Claims claims = getClaimByToken(token);
        if (claims == null) {
            return null;
        }
        return claims.getSubject();
    }
}