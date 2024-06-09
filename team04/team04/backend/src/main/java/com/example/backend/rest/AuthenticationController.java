package com.example.backend.rest;

import com.example.backend.exeptions.NotAcceptableException;
import com.example.backend.models.Custom_User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.token.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;
    JWToken jwToken = new JWToken();

    @PostMapping("/login")
    public ResponseEntity<Custom_User> authenticateAccount(@RequestBody ObjectNode signInInfo) {
        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        Custom_User user = userRepository.findByQuery("user_find_login", email, password);

        if(user == null){
            throw new NotAcceptableException("Could not authenticate user with these credentials.");
        }

        if (email == null || password == null) {
            throw new NotAcceptableException("Vul gegevens in");
        }

        String tokenString = jwToken.encode(password, email);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}
