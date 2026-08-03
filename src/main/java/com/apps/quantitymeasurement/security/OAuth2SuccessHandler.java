package com.apps.quantitymeasurement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    // Where the React (Vite) frontend runs in development.
    private static final String FRONTEND_URL = "http://51.20.29.155:8081";

    public OAuth2SuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        String jwt = jwtService.generateToken(email);

        String encodedToken = URLEncoder.encode(jwt, StandardCharsets.UTF_8);

        // Hand the token back to the SPA instead of printing it as raw
        // JSON — LoginPage reads it from the URL and logs the user in
        // automatically.
        response.sendRedirect(FRONTEND_URL + "/login?token=" + encodedToken);
    }
}