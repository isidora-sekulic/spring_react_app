/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Home PC
 */
@Service
public class JwtService {
    
    @Value("${app.jwt.secret}")
    private String secret;
    
    @Value("${app.jwt.expiration-ms}")
    private Long expirationMs;
    
    private SecretKey key(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(User user, Map<String, String> of) {
        
        Date now=new Date();
        Date exp=new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(of)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key())
                .compact();
                
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith((SecretKey)key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    boolean isValid(String token, UserDetails ud) {
        try {
            final String un=extractUsername(token);
            return un.equals(ud.getUsername()) && !isExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isExpired(String token) {
        
        Date exp=Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getExpiration();
        
        return exp.before(new Date());
    }
    
}
