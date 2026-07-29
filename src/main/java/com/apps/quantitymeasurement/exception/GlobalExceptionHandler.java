package com.apps.quantitymeasurement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//applies to the entire application
@RestControllerAdvice
public class GlobalExceptionHandler {

    // UC17 UPDATE
    // Handle quantity measurement exceptions

    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<Map<String, String>> handleQuantityMeasurementException(
            QuantityMeasurementException exception
    ) {

        Map<String, String> response =
                new HashMap<>();

        response.put(
                "message",
                exception.getMessage()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    // UC17 UPDATE
    // Handle unexpected exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(
            Exception exception
    ) {

        Map<String, String> response =
                new HashMap<>();

        response.put(
                "message",
                "Internal Server Error"
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}