package com.example.DeliveryFeeApplication.weather;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public List<WeatherEntry> getAllWeatherEntries() {
        return weatherRepository.findAll();
    }

    public WeatherEntry getLatestEntryByName(String name) {
        String stationName = switch (name.toLowerCase()) {
            case "tallinn" -> "Tallinn-Harku";
            case "tartu" -> "Tartu-Tõravere";
            case "pärnu" -> "Pärnu";
            default -> throw new IllegalArgumentException("Invalid value for 'city': " + name);
        };
        return weatherRepository.findFirstByNameOrderByTimestampDesc(stationName)
                .orElseThrow(() -> new EntityNotFoundException("No weather entry found for name: " + name));
    }
}
