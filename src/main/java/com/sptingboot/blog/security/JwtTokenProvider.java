package com.sptingboot.blog.security;

import com.sptingboot.blog.exception.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationInMs;

    public String generateToken(Authentication  authentication) {
        String userName =  authentication.getName();
        Date  currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        return Jwts.builder().setSubject(userName)
                    .setIssuedAt(currentDate)
                    .setExpiration(expireDate)
                    .signWith(key()).compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
//    Get the username from the token
    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

//  validate the JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        }catch (ExpiredJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        }catch(UnsupportedJwtException ue) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        }catch (IllegalArgumentException iae) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

}
