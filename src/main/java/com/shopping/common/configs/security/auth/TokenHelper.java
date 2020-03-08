package com.shopping.common.configs.security.auth;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.shopping.common.configs.security.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenHelper {

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String username) {
        Date date = new Date();
        long expiresIn = EXPIRES_IN;
        Date expirationDate = new Date(date.getTime() + expiresIn * 1000);

        return Jwts.builder()
                   .setIssuer(APP_NAME)
                   .setSubject(username)
                   .setIssuedAt(date)
                   .setExpiration(expirationDate)
                   .signWith(SIGNATURE_ALGORITHM, SECRET)
                   .compact();
    }

    public String refreshToken(String token) {
        String refreshedToken;
        Date date = new Date();
        long expiresIn = EXPIRES_IN;
        Date expirationDate = new Date(date.getTime() + expiresIn * 1000);

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(date);
            refreshedToken = Jwts.builder()
                                 .setClaims(claims)
                                 .setExpiration(expirationDate)
                                 .signWith(SIGNATURE_ALGORITHM, SECRET)
                                 .compact();

        } catch (Exception e) {
            refreshedToken = null;
            log.error(e.getMessage());
        }

        return refreshedToken;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
            log.error(e.getMessage());
        }
        return issueAt;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                         .setSigningKey(SECRET)
                         .parseClaimsJws(token)
                         .getBody();
        } catch (Exception e) {
            claims = null;
            log.error(e.getMessage());
        }
        return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (
                username != null &&
                username.equals(userDetails.getUsername()) &&
                !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        Boolean flag = true;
        flag = lastPasswordReset != null && created.before(lastPasswordReset);
        return flag;
    }

    public int getExpiredIn() {
        int expired = EXPIRES_IN;
        return expired;
    }

    public String getToken(HttpServletRequest request) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

}
