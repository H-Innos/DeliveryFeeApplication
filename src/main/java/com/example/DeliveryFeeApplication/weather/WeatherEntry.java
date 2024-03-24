package com.example.DeliveryFeeApplication.weather;

import jakarta.persistence.*;

@Entity
@Table
public class WeatherEntry {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Long timestamp;
    private String name;
    private String phenomenon;
    private int wmoCode;
    private double airTemperature;
    private double windSpeed;

    public WeatherEntry() {
    }

    public WeatherEntry(Long timestamp, String name, String phenomenon, int wmoCode, double airTemperature, double windSpeed) {
        this.timestamp = timestamp;
        this.name = name;
        this.phenomenon = phenomenon;
        this.wmoCode = wmoCode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
    }

    public WeatherEntry(Long id, Long timestamp, String name, String phenomenon, int wmoCode, double airTemperature, double windSpeed) {
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
        this.phenomenon = phenomenon;
        this.wmoCode = wmoCode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
    }

    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public int getWmoCode() {
        return wmoCode;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public boolean isSnowOrSleet() {
        return phenomenon.equals("snow") || phenomenon.equals("sleet");
    }

    public boolean isRain() {
        return phenomenon.equals("rain");
    }

    public boolean isExtremeConditions() {
        return phenomenon.equals("glaze") || phenomenon.equals("hail") || phenomenon.equals("thunder");
    }

    @Override
    public String toString() {
        return "WeatherEntry{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", name='" + name + '\'' +
                ", phenomenon='" + phenomenon + '\'' +
                ", WMOCode=" + wmoCode +
                ", airTemperature=" + airTemperature +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
