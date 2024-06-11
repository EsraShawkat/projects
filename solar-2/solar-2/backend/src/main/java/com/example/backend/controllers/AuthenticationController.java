package com.example.backend.controllers;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.JWTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTokenUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody User loginUser) {
        Map<String, Object> response = new HashMap<>();

        try {
            User user = userRepository.findByEmail(loginUser.getEmail());

            if (user != null && user.getPassword().equals(loginUser.getPassword())) {
                String token = jwtUtils.encode(user.getEmail(), user.getRole());

                response.put("success", true);
                response.put("message", "Login successful!");
                response.put("token", token);
                response.put("userId", user.getId());
                response.put("email", user.getEmail());
                response.put("role", user.getRole());

                return ResponseEntity.accepted()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



}
