package com.example.DeliveryFeeApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(ForbiddenVehicleException.class)
    public ResponseEntity<Object> handleForbiddenVehicleException(ForbiddenVehicleException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
}
