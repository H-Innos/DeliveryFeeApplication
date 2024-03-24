package com.example.DeliveryFeeApplication.weather;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WeatherEntryDTOMapper implements Function<WeatherEntry, WeatherEntryDTO> {

    @Override
    public WeatherEntryDTO apply(WeatherEntry weatherEntry) {
        return new WeatherEntryDTO(
                weatherEntry.getTimestamp(),
                weatherEntry.getName(),
                weatherEntry.getAirTemperature(),
                weatherEntry.getWindSpeed(),
                weatherEntry.isRain(),
                weatherEntry.isSnowOrSleet(),
                weatherEntry.isExtremeConditions()
        );
    }
}
