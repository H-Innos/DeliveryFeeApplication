package com.example.DeliveryFeeApplication.weather;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Interface for reqesting data from the weather database.
 */
@RestController
@Validated
@RequestMapping(path="api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<WeatherEntryDTO> getAllWeatherEntries() {
        return weatherService.getAllWeatherEntries();
    }

    @GetMapping(path="{name}")
    public WeatherEntryDTO getLatestWeatherEntryByName(@PathVariable String name) { return weatherService.getLatestEntryByName(name);}
}
