package com.example.backend.repositories;

import com.example.backend.models.Scooter;
import com.example.backend.models.Trip;

import java.util.List;

public interface TripRepository {
    List<Trip> findAll();

    Trip findById(long id);
    Trip save(Trip trip);
    Trip deleteById(long id);
    List<Trip> findByQuery(String jpqlQuery, Object... params);
}
