package com.fdmgroup.skillify.controller;

import com.fdmgroup.skillify.config.JwtTokenProvider;
import com.fdmgroup.skillify.dto.auth.RefreshTokenRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;

    @Value("${jwt.accessExpirationInMinutes}")
    private int jwtAccessExpirationInMinutes;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@RequestBody RefreshTokenRequestDto requestDto, HttpServletResponse response) {
        String refreshToken = requestDto.getRefreshToken();
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String email = jwtTokenProvider.getEmailFromToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String accessToken = jwtTokenProvider.generateAccessToken(userDetails);

            ResponseCookie authCookie = ResponseCookie.from("Authorization", accessToken)
                    .path("/")
                    .maxAge(jwtAccessExpirationInMinutes * 60)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
