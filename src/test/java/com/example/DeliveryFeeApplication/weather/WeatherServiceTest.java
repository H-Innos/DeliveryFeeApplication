package com.example.DeliveryFeeApplication.weather;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;
    private WeatherService service;
    private final WeatherEntryDTOMapper weatherEntryDTOMapper = new WeatherEntryDTOMapper();
    @BeforeEach
    void setUp() {

        service = new WeatherService(weatherRepository, weatherEntryDTOMapper);
    }

    @Test
    void canGetAllWeatherEntries() {
        service.getAllWeatherEntries();
        verify(weatherRepository).findAll();
    }

    @Test
    void canGetLatestEntryByName() {
        // Test with valid city names
        WeatherEntry mockEntry = new WeatherEntry(1L, "test", "test", 123, 20, 10);

        when(weatherRepository.findFirstByNameOrderByTimestampDesc(anyString()))
                .thenReturn(Optional.of(mockEntry));
        service.getLatestEntryByName("Tallinn");
        service.getLatestEntryByName("Tartu");
        service.getLatestEntryByName("Pärnu");

        verify(weatherRepository, times(3)).findFirstByNameOrderByTimestampDesc(anyString());
    }
    @Test
    void throwsExceptionForInvalidCityName() {
        String name = "Haapsalu";

        assertThatThrownBy(() -> service.getLatestEntryByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid value for 'city': " + name);

    }

    @Test
    void throwsExceptionWhenNoEntryFound() {
        String name = "Tallinn";
        when(weatherRepository.findFirstByNameOrderByTimestampDesc(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getLatestEntryByName(name))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No weather entry found for name: " + name);
    }


}