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

    @GetMapping
    public double getDeliveryFee(
            @RequestParam @NonNull String city,
            @RequestParam @NonNull String vehicle) {
        return deliveryFeeService.getDeliveryFee(city, vehicle);
    }
}
