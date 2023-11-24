package com.fdmgroup.skillify.config;

import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.repository.AppUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

/**
 * This class is responsible for generating and validating JWT tokens.
 */
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refreshExpirationInDays}")
    private int jwtRefreshExpirationInDays;

    @Value("${jwt.accessExpirationInMinutes}")
    private int jwtAccessExpirationInMinutes;

    private AppUserRepository appUserRepository;

    @Autowired
    public JwtTokenProvider(AppUserRepository appUserRepository) {
    	this.appUserRepository = appUserRepository;
    }

    /**
     * Generates a JWT refresh token.
     * @return
     */
    public String generateRefreshToken(UserDetails userDetails) {

        Date now = Date.valueOf(LocalDate.now());
        Date expiryDate = Date.valueOf(now.toLocalDate().plusDays(jwtRefreshExpirationInDays));

        AppUser user = appUserRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Jwts.builder()
                .setId(user.getEmail())
                .claim("role", user.getRole())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("authenticated", user.isAuthenticated())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setHeaderParam("typ", "JWT")
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * Generates a JWT access token.
     * @param userDetails
     * @return
     */
    public String generateAccessToken(UserDetails userDetails) {

        java.util.Date now = new java.util.Date();
        java.util.Date expiryDate = new java.util.Date(now.getTime() + jwtAccessExpirationInMinutes * 60 * 1000);

        AppUser user = appUserRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Jwts.builder()
                .setId(user.getEmail())
                .claim("role", user.getRole())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setHeaderParam("typ", "JWT")
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * Get the user email from the JWT token.
     * @param token
     * @return The user email.
     */
    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Validate the JWT token.
     * @param token
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
