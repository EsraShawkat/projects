package com.example.backend.repositories;

import com.example.backend.models.Scooter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScootersRepositoryMock implements ScootersRepository {
    private List<Scooter> scooters;

    public ScootersRepositoryMock() {
        scooters = new ArrayList<>();
        // Initialize the array with sample scooter data
        for (int i = 1; i <= 7; i++) {
            scooters.add(scooterSample((long) i));
        }
    }

    public static Scooter scooterSample(Long id) {
        int tag = (int) (Math.random() * 90000 + 1);
        int statusNumber = (int) (Math.random() * 3 + 1);
        String status = (statusNumber == 3) ? "IDLE" : (statusNumber == 2) ? "INUSE" : "MAINTENANCE";
        int[] gpsLocationArray = new int[5];
        for (int i = 0; i < 5; i++) {
            gpsLocationArray[i] = (int) (Math.random() * 99);
        }
        String gpsLocation = String.format("%d.%d.%d.%d.%d", gpsLocationArray[0], gpsLocationArray[1],
                gpsLocationArray[2], gpsLocationArray[3], gpsLocationArray[4]);

        int mileage = (int) (Math.random() * 9000 + 1);
        int batteryCharge = (int) (Math.random() * 100 + 5);

        return new Scooter(id, String.valueOf(tag), status, gpsLocation, mileage, batteryCharge);
    }

    @Override
    public List<Scooter> findAll() {
        return scooters;
    }

    @Override
    public Scooter findById(long id) {
        for (Scooter scooter: scooters) {
            if (scooter.getId() == id){
                return scooter;
            }
        }
        return null;
    }

    @Override
    public Scooter save(Scooter scooter) {
        if(scooter.getId() == 0){
            int id = 1;
            while(findById(id) != null){
                id++;
            }
            scooter.setId((long) id);
            scooters.add(scooter);
            return scooter;
        }
        for (int i = 0; i < scooters.size(); i++) {
            if (scooters.get(i).getId().equals(scooter.getId())) {
                scooters.set(i, scooter);
                return scooter;
            }
        }
        return scooter;
    }

    @Override
    public Scooter deleteById(long id) {
        Scooter scooterToDelete = findById(id);
        if (scooterToDelete != null){
            scooters.remove(findById(id));
            return scooterToDelete;
        }
        return null;
    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        return null;
    }
}
