package com.example.backend.repositories;

import com.example.backend.models.Scooter;
import java.util.List;

public interface ScootersRepository {
    List<Scooter> findAll();

    Scooter findById(long id);
    Scooter save(Scooter scooter);
    Scooter deleteById(long id);
    List<Scooter> findByQuery(String jpqlName, Object... params);
}
