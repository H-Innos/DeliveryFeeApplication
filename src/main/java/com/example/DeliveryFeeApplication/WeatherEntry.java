package com.example.DeliveryFeeApplication;

public class WeatherEntry {
    private Long id;
    private Long timestamp;
    private String name;
    private String phenomenon;
    private int WMOCode;
    private double airTemperature;
    private double windSpeed;
    private boolean isSnowofSleet;
    private boolean isRain;
    private boolean isExtremeConditions;

    public WeatherEntry() {
    }

    public WeatherEntry(Long timestamp, String name, String phenomenon, int WMOCode, double airTemperature, double windSpeed) {
        this.timestamp = timestamp;
        this.name = name;
        this.phenomenon = phenomenon;
        this.WMOCode = WMOCode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
    }

    public WeatherEntry(Long id, Long timestamp, String name, String phenomenon, int WMOCode, double airTemperature, double windSpeed) {
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
        this.phenomenon = phenomenon;
        this.WMOCode = WMOCode;
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

    public int getWMOCode() {
        return WMOCode;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public boolean isSnowofSleet() {
        return phenomenon.equals("snow") || phenomenon.equals("sleet");
    }

    public boolean isRain() {
        return phenomenon.equals("rain");
    }

    public boolean isExtremeConditions() {
        return phenomenon.equals("glaze") || phenomenon.equals("hail") || phenomenon.equals("thunder");
    }
}
