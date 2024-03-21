package com.example.DeliveryFeeApplication.weather;

import com.example.DeliveryFeeApplication.weather.WeatherEntry;
import com.example.DeliveryFeeApplication.weather.WeatherRepository;
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
}
