package com.example.DeliveryFeeApplication.exception;

public class ForbiddenVehicleException extends RuntimeException {
    public ForbiddenVehicleException(String message) {
        super(message);
    }
}
