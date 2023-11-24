package com.fdmgroup.skillify.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter class is used to authenticate the user and generate the JWT token.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    private int jwtAccessExpirationInMinutes = 15;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    /**
     * This method is used to authenticate the user by reading the email and password from the request body.
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper ObjectMapper = new ObjectMapper();
            JsonNode jsonNode = ObjectMapper.readTree(request.getInputStream());
            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

            return authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email/password");
        }
    }

    /**
     * This method is used to generate the JWT tokens and set it in the response cookie and
     * response body after successful authentication.
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws BadCredentialsException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();

        String token = tokenProvider.generateAccessToken(userDetails);

        ResponseCookie authCookie = ResponseCookie.from("Authorization", token)
                .path("/")
                .maxAge(jwtAccessExpirationInMinutes * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

        String refreshToken = tokenProvider.generateRefreshToken(userDetails);
        String refreshTokenJson = "{\"refreshToken\": \"" + refreshToken + "\"}";
        response.getWriter().write(refreshTokenJson);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
