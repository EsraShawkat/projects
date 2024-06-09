package com.example.backend.rest;

import com.example.backend.exeptions.PreConditionFailedException;
import com.example.backend.exeptions.ResourceNotFoundException;
import com.example.backend.models.Scooter;
import com.example.backend.models.Trip;
import com.example.backend.repositories.ScootersRepository;
import com.example.backend.repositories.TripRepositoryJpa;
import com.example.backend.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scooters")
public class ScooterController {

    @Autowired
    private ScootersRepository scootersRepository;
    @Autowired
    private TripRepositoryJpa tripRepositoryJpa;

    public ScooterController(ScootersRepository scootersRepository, TripRepositoryJpa tripRepositoryJpa) {

        this.scootersRepository = scootersRepository;
        this.tripRepositoryJpa = tripRepositoryJpa;
    }

    @GetMapping("/summary")
    @JsonView(value = View.scooterView.External.class)
    public List<Scooter> getScootersSummery() {
        List<Scooter> scooters = scootersRepository.findAll();
        if (scooters.isEmpty()) {
            throw new ResourceNotFoundException("No scooters found");
        }
        return scooters;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scooter> getScooterById(@PathVariable long id){
        Scooter scooter = scootersRepository.findById(id);
        System.out.println(scooter);
        if(scooter == null){
            throw new ResourceNotFoundException("Scooter with id: " + id + " not found");
        }

        return ResponseEntity.ok(scooter);
    }

    @PostMapping
    public ResponseEntity<Scooter> createScooter(@RequestBody Scooter scooter) {
        Scooter savedScooter = scootersRepository.save(scooter);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedScooter.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedScooter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scooter> updateScooter(@PathVariable Long id, @RequestBody Scooter updatedScooter) {
        if (!id.equals(updatedScooter.getId())) {
            throw new PreConditionFailedException("ID in the path does not match the ID in the request body");
        }
        scootersRepository.save(updatedScooter);

        return ResponseEntity.ok(updatedScooter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Scooter> DeleteScooter(@PathVariable long id){
        Scooter scooter = scootersRepository.findById(id);

        if(scooter == null){
            throw new ResourceNotFoundException("Scooter with id: " + id + " not found");
        }
        scootersRepository.deleteById(id);
        return ResponseEntity.ok(scooter);
    }

    @PostMapping("/{scooterId}/trips")
    public ResponseEntity<Scooter> scooterTrip(@PathVariable long scooterId, @RequestBody Trip trip){
        Scooter scooter = scootersRepository.findById(scooterId);

        if (scooter == null) {
            throw new ResourceNotFoundException("Scooter with id: " + scooterId + " not found");
        }

        if (!scooter.getStatus().equals(Scooter.Status.IDLE.toString()) || scooter.getBatteryCharge() < 10) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }

        if (trip.getStartDate() == null) {
            trip.setStartDate(LocalDateTime.now());
        }

        trip.setStartPosition(scooter.getGpsLocation());
        scooter.setStatus(Scooter.Status.INUSE.toString());
        scooter.setCurrentTrip(trip);

        scootersRepository.save(scooter);

        return new ResponseEntity<>(scooter, HttpStatus.OK);
    }
    @GetMapping("/getByQuery/{name}")
    public ResponseEntity<List<?>> getByQuery(@PathVariable String name, @RequestBody Object... params){
        List<?> result = null;

        if (name.startsWith("Scooter_")) {
            result = scootersRepository.findByQuery(name, params);
        } else if (name.startsWith("Trip_")) {
            result = tripRepositoryJpa.findByQuery(name, params);
        }

        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Scooter>> getScooters(@RequestParam(required = false) Integer battery, @RequestParam(required = false) String status) {
        if (battery != null && status != null) {
            throw new ResourceNotFoundException("Bad Request: Cannot provide both 'battery' and 'status' parameters");
        }

        List<Scooter> result;

        if (battery != null) {
            result = findScootersByBatteryCharge(battery);
        } else if (status != null) {
            result = findScootersByStatus(status);
        } else {
            result = scootersRepository.findAll();
        }

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private List<Scooter> findScootersByBatteryCharge(int battery) {
        return scootersRepository.findByQuery("Scooter_find_by_battery", battery);
    }

    private List<Scooter> findScootersByStatus(String status) {
        if(!(status.equals("INUSE") || status.equals("IDLE") || status.equals("MAINTENANCE"))){
            throw new ResourceNotFoundException("status given is not in scooter");
        }
        return scootersRepository.findByQuery("Scooter_find_by_status", status);
    }
    @GetMapping("/{scooterId}/trips")
    public ResponseEntity<List<Trip>> getTripsByDateTimeRange(@PathVariable("scooterId") Long scooterId,
                                                              @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                              @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to) {
        List<Trip> trips = tripRepositoryJpa.findByQuery("Trip_by_period", scooterId, from, to);

        if (trips.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }
}


