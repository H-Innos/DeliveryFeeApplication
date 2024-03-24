package com.example.DeliveryFeeApplication.weather;

public class WeatherEntryDTO {
    long timestamp;
    String name;
    double airTemperature;
    double windSpeed;
    boolean isRain;
    boolean isSnowOrSleet;
    boolean isExtremeConditions;

    public WeatherEntryDTO(long timestamp,String name, double airTemperature, double windSpeed, boolean isRain, boolean isSnowOrSleet, boolean isExtremeConditions) {
        this.timestamp = timestamp;
        this.name = name;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.isRain = isRain;
        this.isSnowOrSleet = isSnowOrSleet;
        this.isExtremeConditions = isExtremeConditions;
    }

    public String getName() {
        return name;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public boolean isRain() {
        return isRain;
    }

    public boolean isSnowOrSleet() {
        return isSnowOrSleet;
    }

    public boolean isExtremeConditions() {
        return isExtremeConditions;
    }
}
