package com.example.DeliveryFeeApplication.deliveryFee;

import com.example.DeliveryFeeApplication.enums.City;
import com.example.DeliveryFeeApplication.enums.Vehicle;
import com.example.DeliveryFeeApplication.WeatherEntry;
import com.example.DeliveryFeeApplication.exception.ForbiddenVehicleException;
import com.example.DeliveryFeeApplication.exception.InvalidParameterException;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService {
    public Double getDeliveryFee(String cityName, String vehicleType) {
        WeatherEntry weatherEntry = new WeatherEntry();
        City city = switch (cityName.toLowerCase()) {
            case "tallinn" -> City.TALLINN;
            case "tartu" -> City.TARTU;
            case "pärnu" -> City.PÄRNU;
            default -> throw new InvalidParameterException("Invalid value for parameter 'city': " + cityName);
        };
        Vehicle vehicle = switch (vehicleType.toLowerCase()) {
            case "car" -> Vehicle.CAR;
            case "scooter" -> Vehicle.SCOOTER;
            case "bike" -> Vehicle.BIKE;
            default -> throw new InvalidParameterException("Invalid value for parameter 'vehicle': " + vehicleType);
        };
        if (weatherEntry.getWindSpeed() > 20 && vehicle.isForbiddenWhenExtremeConditions())
            throw new ForbiddenVehicleException("Usage of selected vehicle type is forbidden");

        return calculateDeliveryFee(city, vehicle, weatherEntry);
    }

    private static double calculateDeliveryFee(City city, Vehicle vehicle, WeatherEntry weatherEntry) {
        double deliveryFee = city.getRbf() + vehicle.getRbfIncrement();
        if (weatherEntry.getAirTemperature() < -10)
            deliveryFee += vehicle.getATEFLessThanNegative10();
        if (weatherEntry.getAirTemperature() < 0)
            deliveryFee += vehicle.getATEFNegative10to0();
        if (weatherEntry.getWindSpeed() > 10)
            deliveryFee += vehicle.getWSEFBetween10and20();
        if (weatherEntry.isSnowofSleet())
            deliveryFee += vehicle.getWPEFSnowAndSleet();
        if (weatherEntry.isRain())
            deliveryFee += vehicle.getWPEFRain();
        return deliveryFee;
    }
}