package com.example.backend.repositories;

import com.example.backend.exeptions.ResourceNotFoundException;
import com.example.backend.models.Scooter;
import com.example.backend.models.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
@Transactional
public class TripRepositoryJpa implements TripRepository{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Trip> findAll() {
        TypedQuery<Trip> query = entityManager.createQuery("SELECT t FROM Trip t", Trip.class);
        List<Trip> result = query.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result;
    }


    @Override
    public Trip findById(long id) {
        return entityManager.find(Trip.class, id);
    }

    @Override
    public Trip save(Trip trip) {
        entityManager.merge(trip);
        return trip;
    }

    @Override
    public Trip deleteById(long id) {
        Trip trip = findById(id);
        if(trip != null){
            entityManager.remove(trip);
        }
        return trip;
    }

    @Override
    public List<Trip> findByQuery(String jpqlQuery, Object... params) {
        TypedQuery<Trip> query = jpqlQuery.equals("Trip_by_period") ? entityManager.createNamedQuery("Trip_find_by_scooterId_and_period", Trip.class)
                : null;
        if(query == null){
            return null;
        }
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return query.getResultList();
    }
}
