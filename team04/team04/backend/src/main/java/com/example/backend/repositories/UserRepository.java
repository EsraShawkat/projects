package com.example.backend.repositories;


import com.example.backend.models.Custom_User;

public interface UserRepository {
    Custom_User save(Custom_User user);
    Custom_User findByQuery(String query, Object... params);
}
