package com.example.DeliveryFeeApplication.deliveryFee;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path="api/deliveryFee")
public class DeliveryFeeController {
    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }

    /**
     * Returns delivery fee according to request paramters.
     * @param city      request parameter for city name
     * @param vehicle   request parameter for vehicle type
     * @return          delivery fee
     */
    @GetMapping
    public double getDeliveryFee(
            @RequestParam @NonNull String city,
            @RequestParam @NonNull String vehicle) {
        return deliveryFeeService.getDeliveryFee(city, vehicle);
    }
}
