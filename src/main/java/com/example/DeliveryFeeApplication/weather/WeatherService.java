package com.example.DeliveryFeeApplication.weather;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherEntryDTOMapper weatherEntryDTOMapper;

    public WeatherService(WeatherRepository weatherRepository, WeatherEntryDTOMapper weatherEntryDTOMapper) {
        this.weatherRepository = weatherRepository;
        this.weatherEntryDTOMapper = weatherEntryDTOMapper;
    }

    public List<WeatherEntryDTO> getAllWeatherEntries() {
        return weatherRepository.findAll()
                .stream()
                .map(weatherEntryDTOMapper)
                .toList();
    }

    public WeatherEntryDTO getLatestEntryByName(String name) {
        String stationName = switch (name.toLowerCase()) {
            case "tallinn" -> "Tallinn-Harku";
            case "tartu" -> "Tartu-Tõravere";
            case "pärnu" -> "Pärnu";
            default -> throw new IllegalArgumentException("Invalid value for 'city': " + name);
        };
        return weatherRepository.findFirstByNameOrderByTimestampDesc(stationName).map(weatherEntryDTOMapper)
                .orElseThrow(() -> new EntityNotFoundException("No weather entry found for name: " + name));
    }
}
