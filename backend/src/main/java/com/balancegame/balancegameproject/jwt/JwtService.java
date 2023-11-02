package com.balancegame.balancegameproject.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.Signature;
import java.util.Date;

public class JwtService {
    private static final String SECRET_KEY = "fdsfdsfdsf";
    public String createToken(String id, long expTime){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setId(id)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }
    public String getId(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getId();
        }catch (ExpiredJwtException e){
            throw new RuntimeException("토큰이 만료되었습니다.");
        }catch (JwtException e){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
}
