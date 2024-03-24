package com.example.DeliveryFeeApplication.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Custom response payload for exceptions.
 */
public record ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
}
