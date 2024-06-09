package com.example.backend.models;
import jakarta.persistence.*;

import java.util.concurrent.ThreadLocalRandom;
@Entity
@NamedQuery(name = "user_find_login", query = "SELECT u FROM Custom_User u where u.email = ?1 AND u.hashedPassword = ?2 ")
public class Custom_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String hashedPassword;
    private String role;

    public Custom_User(String email, String hashedPassword) {
        this.id = 0;
        this.name = extractNameFromEmail(email);
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = getRandomRole();
    }

    public Custom_User() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getRole() {
        return role;
    }
    private String getRandomRole() {
        int randomInt = (int) (Math.random() * 2);
        return randomInt == 1 ? "user" : "admin";
    }

    private String extractNameFromEmail(String email) {
        String[] parts = email.split("@");
        return parts[0];
    }
}
