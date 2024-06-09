package com.example.backend.models;
import com.example.backend.views.View;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@NamedQuery(name = "Scooter_find_by_status", query = "SELECT s FROM Scooter s JOIN FETCH s.trips WHERE s.status = ?1 ")
@NamedQuery(name = "Scooter_find_by_battery", query = "SELECT s FROM Scooter s JOIN FETCH s.trips WHERE s.batteryCharge < ?1")
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @JsonView(value = {View.scooterView.External.class})
    private Long id;
    
    @JsonView(value = {View.scooterView.External.class})
    private String tag;
    
    @JsonView(value = {View.scooterView.External.class})
    private String status;
    
    private String gpsLocation;
    private int mileage;
    @JsonView(value = {View.scooterView.External.class})
    private int batteryCharge;
    @JsonManagedReference
    @OneToMany(mappedBy = "scooter", fetch = FetchType.LAZY)
    private List<Trip> trips = new ArrayList<>();
    
    @OneToOne(fetch = FetchType.LAZY)
    private Trip currentTrip;
    public Scooter() {
    }

    public enum Status {
        IDLE, INUSE, MAINTENANCE

    }

    public Scooter(Long id, String tag, String status, String gpsLocation, int mileage, int batteryCharge) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.gpsLocation = gpsLocation;
        this.mileage = mileage;
        this.batteryCharge = batteryCharge;
    }
    public void addTrip(Trip trip) {
        trip.setScooter(this);
        trips.add(trip);
    }

    // Method to remove a trip from the scooter's list of trips
    public void removeTrip(Trip trip) {
        trips.remove(trip);
        trip.setScooter(null);
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(Trip trip) {
        if (trip != null && !this.equals(trip.getScooter())) {
            this.currentTrip = trip;
            trip.setScooterInUse(this);
        }
    }

    // Method to clear the current trip associated with the scooter
    public void clearCurrentTrip() {
        if (currentTrip != null) {
            currentTrip.setScooterInUse(null);
            this.currentTrip = null;
        }
    }

    public static Scooter copyConstructor(Scooter scooter) {
        if (scooter == null) return null;
        return new Scooter(scooter.getId(), scooter.getTag(), scooter.getStatus(), scooter.getGpsLocation(),
                scooter.getMileage(), scooter.getBatteryCharge());
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }
}
