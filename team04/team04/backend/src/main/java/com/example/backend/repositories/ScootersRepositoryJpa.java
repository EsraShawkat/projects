package com.example.backend.repositories;

import com.example.backend.exeptions.ResourceNotFoundException;
import com.example.backend.models.Scooter;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Primary
@Repository
@Transactional
public class ScootersRepositoryJpa implements ScootersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Scooter> findAll() {

        TypedQuery<Scooter> query = entityManager.createQuery("SELECT s FROM Scooter s JOIN FETCH s.trips", Scooter.class);
        return query.getResultList();
    }

    @Override
    public Scooter findById(long id) {
        String jpql = "SELECT s FROM Scooter s JOIN FETCH s.trips WHERE s.id = :id";
         Query query = entityManager.createQuery(jpql, Scooter.class);
        query.setParameter("id", id);

        try {
            return (Scooter) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Scooter save(Scooter scooter) {
        entityManager.merge(scooter);
        return scooter;
    }

    @Override
    public Scooter deleteById(long id) {
         Scooter scooter = findById(id);
         if (scooter != null) {
            entityManager.remove(scooter);
         }
        return scooter;
    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Scooter> typedQuery = jpqlName.equals("Scooter_find_by_status") ? entityManager.createNamedQuery("Scooter_find_by_status", Scooter.class)
                : jpqlName.equals("Scooter_find_by_battery") ?  entityManager.createNamedQuery("Scooter_find_by_battery", Scooter.class)
                : null;

        if(typedQuery == null){
            return null;
        }

        for (int i = 0; i < params.length; i++) {
            typedQuery.setParameter(i + 1, params[i]);
        }

        return typedQuery.getResultList();
    }

    public Scooter createSampleScooter(long id){
        Random random = new Random();

        int tag = random.nextInt(90000) + 1;

        String status;
        int statusNumber = random.nextInt(3) + 1;
        status = statusNumber == 1 ? "MAINTENANCE"
                : statusNumber == 2 ? "INUSE"
                : "IDLE";

        int[] gpsLocationArray = new int[5];
        for (int i = 0; i < 5; i++) {
            gpsLocationArray[i] = random.nextInt(99);
        }
        String gpsLocation = gpsLocationArray[0] + "." + gpsLocationArray[1] + "."
                + gpsLocationArray[2] + "." + gpsLocationArray[3] + "." + gpsLocationArray[4];

        int mileage = random.nextInt(9000) + 1;
        int batteryCharge = random.nextInt(96) + 5;

        return new Scooter(id, String.valueOf(tag), status, gpsLocation, mileage, batteryCharge);
    }
}
