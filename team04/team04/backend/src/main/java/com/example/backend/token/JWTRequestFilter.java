package com.example.backend.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private static final Set<String> SECURED_PATHS =
            Set.of( "/scooters" );

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {


        String path = req.getServletPath();

        if (HttpMethod.OPTIONS.matches(req.getMethod()) ||
                SECURED_PATHS.stream().noneMatch(path::startsWith)) {
            chain.doFilter(req, res);
            return;
        }

        String encodedToken = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (encodedToken == null) {
            System.out.println("Got a request without a token");
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
            return;
        }

        encodedToken = encodedToken.replace("Bearer ", "");
        JWToken tokenInfo = new JWToken();
        try {
            tokenInfo = tokenInfo.decode(encodedToken);
        } catch (RuntimeException | AuthenticationException e) {
            System.out.println("Authentication error: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
            return;
        }

        req.setAttribute(tokenInfo.getPassPhrase(), tokenInfo);

        chain.doFilter(req, res);
    }

}
