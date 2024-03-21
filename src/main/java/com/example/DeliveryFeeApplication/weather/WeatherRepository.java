package com.example.DeliveryFeeApplication.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository
        extends JpaRepository<WeatherEntry, Long> {
}
