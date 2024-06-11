package com.example.backend.controllers;

import com.example.backend.exceptions.NotFoundException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.*;
import com.example.backend.repositories.ForecastRepository;
import com.example.backend.repositories.InventoryRepository;
import com.example.backend.repositories.OrdersRepository;
import com.example.backend.repositories.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling HTTP requests related to forecast.
 */
@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private ForecastRepository forecastRepository;

    @Autowired
    public ForecastController(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    /**
     * Get a list of all orders.
     *
     * @return List of orders.
     * @throws NotFoundException If no orders are found.
     */
    @GetMapping
    public List<Forecast> getAllForecast() {
        // Get all orders
        List<Forecast> forecast = forecastRepository.findAll();
        // Check if orders exist
        if (forecast == null) {
            // Throw an exception if no orders are found
            throw new NotFoundException();
        }
        return forecast;
    }

    /**
     * Get a specific forecast by ID.
     *
     * @param forecastId The ID of the forecast to retrieve.
     * @return The forecast with the given ID.
     */
    @GetMapping("/{forecastId}")
    public Forecast getForecastById(@PathVariable int forecastId) {
        Forecast forecast = forecastRepository.findById(forecastId);
        if (forecast == null){
            throw new ResourceNotFoundException("Forecast " + forecastId + " not found");
        }
        // Find the forecast by ID
        return forecast;
    }

    /**
     * Get a list of orders associated with a specific project.
     *
     * @param id The ID of the project.
     * @return List of orders associated with the project.
     */
    @GetMapping("/orders/{id}")
    public List<Forecast> getForecastByOrderId(@PathVariable int id) {
        // Filter forecasts by order ID
        return forecastRepository.findAll().stream()
                .filter(forecast -> forecast.getOrders().getId() == id)
                .collect(Collectors.toList());
    }

    /**
     * Add orders to the system.
     *
     * @param forecasts List of orders to add.
     * @return The added orders.
     */
    @PostMapping("")
    public Forecast addForecast(@RequestBody List<Forecast> forecasts) {
        for (Forecast forecast : forecasts) {

            // Save the order to the repository
            return forecastRepository.save(forecast);
        }
        return null;
    }
}
