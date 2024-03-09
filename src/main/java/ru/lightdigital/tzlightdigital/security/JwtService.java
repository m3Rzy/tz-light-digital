package ru.lightdigital.tzlightdigital.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.user.model.User;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "jlEqKYIs8cRn/W4wmMDZyffXZyR6uMbo7j6hKc6tY11lY/DD/+1DIGe6hX1xuoMq";

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getPhone())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isValid(String token, UserDetails userDetails) {
        String phone = extractPhone(token);
        return (phone.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractPhone(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
