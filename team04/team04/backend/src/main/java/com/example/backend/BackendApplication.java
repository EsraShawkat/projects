package com.example.backend;

import com.example.backend.models.Custom_User;
import com.example.backend.models.Scooter;
import com.example.backend.models.Trip;
import com.example.backend.repositories.ScootersRepositoryJpa;
import com.example.backend.repositories.TripRepositoryJpa;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    @Autowired
    private ScootersRepositoryJpa scootersRepositoryJpa;
    @Autowired
    private TripRepositoryJpa tripRepositoryJpa;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    private void CreateInitialScooters(){
        List<Scooter> scooters = this.scootersRepositoryJpa.findAll();
        Custom_User user1 = new Custom_User("John@test.nl", "password");
        Custom_User user2 = new Custom_User("Piet@test.nl", "w8woord");
        Custom_User user3 = new Custom_User("Shariq@test.nl", "test123");
        if(scooters.size() > 0) return;
        System.out.println("Configuring some initial scooter data...");
        for (int i = 0; i < 11; i++) {
            Scooter scooter = scootersRepositoryJpa.createSampleScooter(0);
            Scooter savedScooter = this.scootersRepositoryJpa.save(scooter);
            for (int j = 0; j < 2; j++) {
                Trip trip = tripRepositoryJpa.save(Trip.createSampleTrip(0));
                scooter.addTrip(trip);
                tripRepositoryJpa.save(trip);
            }
        }
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running Commandline Startup");
        this.CreateInitialScooters();
    }
}
