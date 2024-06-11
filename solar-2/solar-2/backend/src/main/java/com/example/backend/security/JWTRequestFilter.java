//package com.example.backend.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JWTRequestFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JWTokenUtils tokenUtils;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
//        // Handle preflight requests
//        if (req.getMethod().equals(HttpMethod.OPTIONS.name())) {
//            handlePreflightRequest(res);
//            return;
//        }
//
//        // Continue with the filter chain for all other requests
//        String encryptedToken = req.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (encryptedToken != null) {
//            try {
//                // Decode the token
//                JWTokenInfo jwTokenInfo = JWTokenUtils.decode(encryptedToken.replace("Bearer", "").trim(), this.tokenUtils.getPassphrase());
//
//                // Access control based on UserType
//                String userRole = jwTokenInfo.isRole();
//                if (!hasAccess(userRole, req.getServletPath())) {
//                    sendForbiddenResponse(res, "Access forbidden.");
//                    return;
//                }
//
//
//            } catch (RuntimeException e) {
//                sendUnauthorizedResponse(res, e.getMessage() + " You need to log in first.");
//                return;
//            }
//        }
//
//        chain.doFilter(req, res);
//    }
//
//    private boolean hasAccess(String userRole, String servletPath) {
//        // Assuming the user class has attributes like role, etc.
//        return switch (userRole) {
//            case "admin" -> true; // Admin can access all paths
//            case "user" -> !servletPath.contains("admin"); // User can access paths without "admin"
//            default -> false;
//        };
//    }
//
//
//    private void handlePreflightRequest(HttpServletResponse response) {
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8080");
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, " + HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//
//    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
//    }
//
//
//    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
//    }
//
//
//}
//
