package com.example.DeliveryFeeApplication.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface WeatherRepository
        extends JpaRepository<WeatherEntry, Long> {

    Optional<WeatherEntry> findFirstByNameOrderByTimestampDesc(String name);

}
