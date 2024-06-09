package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@NamedQuery(name = "Trip_find_by_scooterId_and_period", query = "SELECT t FROM Trip t WHERE t.scooter.id = ?1 AND t.startDate >= ?2 AND t.endDate <= ?3")
public class Trip {
    @Id
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private String startPosition;
    
    private String endPosition;
    
    private double mileage;
    
    private double price;
    
    @JsonBackReference
    @ManyToOne()
    private Scooter scooter;


    public Trip(){
    }

    public Trip(Long id, LocalDateTime startDate,
                LocalDateTime endDate, String startPosition,
                String endPosition, double mileage, double price) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.mileage = mileage;
        this.price = price;
    }

    public static Trip createSampleTrip(long id){
        Trip trip = new Trip();
        double milage = Math.random() * 10;

        return  new Trip(id,
                trip.getRandomDateTime(),
                trip.getRandomDateTime(),
                trip.getRandomGPS(),
                trip.getRandomGPS(),
                milage,
                milage * 0.20);
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setScooter(Scooter scooter) {
        if (scooter != null && !scooter.getTrips().contains(this)) {
            this.scooter = scooter;
        }
    }
    private LocalDateTime getRandomDateTime(){
        return LocalDateTime.of(2023,
                (int) Math.ceil(Math.random() * 11),
                (int) Math.ceil(Math.random() * 28),
                (int) Math.ceil(Math.random() * 23),
                (int) Math.ceil(Math.random() * 59),
                (int) Math.ceil(Math.random() * 59));
    }
    private String getRandomGPS(){
        Random random = new Random();
        int[] gpsLocationArray = new int[5];
        for (int i = 0; i < 5; i++) {
            gpsLocationArray[i] = random.nextInt(99);
        }
        return gpsLocationArray[0] + "." + gpsLocationArray[1] + "."
                + gpsLocationArray[2] + "." + gpsLocationArray[3] + "." + gpsLocationArray[4];
    }

    public void setScooterInUse(Scooter scooter) {
        if (scooter != null && !this.equals(scooter) && scooter.getStatus().equals("INUSE")) {
            this.scooter = scooter;
        }
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public double getMileage() {
        return mileage;
    }

    public double getPrice() {
        return price;
    }

    public Scooter getScooter() {
        return scooter;
    }

}
