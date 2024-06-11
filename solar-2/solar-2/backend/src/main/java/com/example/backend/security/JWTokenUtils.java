package com.example.backend.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTokenUtils {


    public static final String JWT_ROLE_CLAIM = "role";

    @Value("${jwt.issuer:MyOrganisation}")
    private String issuer;

    @Value("${jwt.pass-phrase}")
    private String passphrase;

    @Value("${jwt.expiration-seconds}")
    private int expiration;

    @Value("${jwt.refresh-expiration-seconds}")
    private int refreshExpiration;

    public JWTokenUtils() {

    }

    public JWTokenUtils(String s, String testIssuer, long testExpiration) {
    }


    public String encode(String email, String role) {

        Key secretKey = getKey(passphrase);

        return Jwts.builder()
                .setSubject(email)
                .claim(JWT_ROLE_CLAIM, role)
                .setIssuer(issuer) // registered claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private static Key getKey(String passPhrase) {
        byte[] hmacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }


    /**
     * Decode the given token, returning an object with useful token data
     *
     */
    public static JWTokenInfo decode(String token, String passphrase) throws ExpiredJwtException, MalformedJwtException {
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        Claims claims = jws.getBody();

        JWTokenInfo jwTokenInfo = new JWTokenInfo();
        jwTokenInfo.setEmail(claims.getSubject());
        jwTokenInfo.setRole(claims.get(JWT_ROLE_CLAIM, String.class));
        jwTokenInfo.setIssuedAt(claims.getIssuedAt());
        jwTokenInfo.setExpiration(claims.getExpiration());

        return jwTokenInfo;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getExpiration() {
        return expiration;
    }

    public int getRefreshExpiration() {
        return refreshExpiration;
    }
}
