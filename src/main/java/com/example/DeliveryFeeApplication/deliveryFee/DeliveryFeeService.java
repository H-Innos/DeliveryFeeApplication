package com.example.DeliveryFeeApplication.deliveryFee;

import com.example.DeliveryFeeApplication.weather.WeatherEntry;
import com.example.DeliveryFeeApplication.exception.ForbiddenVehicleException;
import com.example.DeliveryFeeApplication.exception.InvalidParameterException;
import com.example.DeliveryFeeApplication.weather.WeatherService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService {

    private final WeatherService weatherService;

    public DeliveryFeeService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Returns the delivery fee according to the location and vehicle type, considering weather conditions.
     * @param cityName      city name
     * @param vehicleType   vehicle type
     * @return              delivery fee
     */
    public Double getDeliveryFee(String cityName, String vehicleType) {
        WeatherEntry weatherEntry = weatherService.getLatestEntryByName(cityName);

        City city = switch (cityName.toLowerCase()) {
            case "tallinn" -> City.TALLINN;
            case "tartu" -> City.TARTU;
            case "pärnu" -> City.PÄRNU;
            default -> throw new InvalidParameterException("Invalid value for parameter 'city': " + cityName);
        };
        Vehicle vehicle = getVehicle(vehicleType, weatherEntry);

        return calculateDeliveryFee(city, vehicle, weatherEntry);
    }

    /**
     * Returns the Vehicle value that corresponds to the name, and throws an exeption when the usage of the vehicle is forbidden.
     * @param vehicleType   vehicle type
     * @param weatherEntry  latest weather entry
     * @return              Vehicle value
     */
    private static Vehicle getVehicle(String vehicleType, WeatherEntry weatherEntry) {
        Vehicle vehicle = switch (vehicleType.toLowerCase()) {
            case "car" -> Vehicle.CAR;
            case "scooter" -> Vehicle.SCOOTER;
            case "bike" -> Vehicle.BIKE;
            default -> throw new InvalidParameterException("Invalid value for parameter 'vehicle': " + vehicleType);
        };
        if (weatherEntry.getWindSpeed() > 20 && vehicle.isForbiddenWhenWindSpeedOver20())
            throw new ForbiddenVehicleException("Usage of selected vehicle type is forbidden");
        if (weatherEntry.isExtremeConditions() && vehicle.isForbiddenWhenExtremeConditions())
            throw new ForbiddenVehicleException("Usage of selected vehicle type is forbidden");
        return vehicle;
    }

    /**
     * Calculates the delivery fee according to location, vehicle and weather conditions.
     * @param city          location
     * @param vehicle       vehicle
     * @param weatherEntry  latest weather conditions in location
     * @return              delivery fee
     */
    private static double calculateDeliveryFee(City city, Vehicle vehicle, WeatherEntry weatherEntry) {
        double deliveryFee = city.getRbf() + vehicle.getRbfIncrement(); // regional base fee + vehicle increment
        if (weatherEntry.getAirTemperature() < -10)
            deliveryFee += vehicle.getATEFLessThanNegative10();
        else if (weatherEntry.getAirTemperature() <= 0)
            deliveryFee += vehicle.getATEFNegative10to0();
        if (weatherEntry.getWindSpeed() >= 10)
            deliveryFee += vehicle.getWSEFBetween10and20();
        if (weatherEntry.isSnowOrSleet())
            deliveryFee += vehicle.getWPEFSnowOrSleet();
        if (weatherEntry.isRain())
            deliveryFee += vehicle.getWPEFRain();
        return deliveryFee;
    }
}