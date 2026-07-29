package com.apps.quantitymeasurement.enums;

import java.util.function.Function;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            celsius -> celsius,
            celsius -> celsius
    ),

    FAHRENHEIT(
            fahrenheit ->
                    (fahrenheit - 32) * 5 / 9,
            celsius ->
                    (celsius * 9 / 5) + 32
    ),

    KELVIN(
            kelvin ->
                    kelvin - 273.15,
            celsius ->
                    celsius + 273.15
    );

    // UC14 UPDATE
    // Conversion lambdas using Celsius as base unit

    private final Function<Double, Double>
            toCelsius;

    private final Function<Double, Double>
            fromCelsius;

    TemperatureUnit(
            Function<Double, Double> toCelsius,
            Function<Double, Double> fromCelsius
    ) {

        this.toCelsius =
                toCelsius;

        this.fromCelsius =
                fromCelsius;
    }

    // UC14 UPDATE
    // Temperature does not support arithmetic

    @Override
     public boolean supportsArithmetic() {
    return false;
}

    @Override
    public double getConversionFactor() {

        return 1.0;
    }

    // UC14 UPDATE
    // Convert temperature to Celsius base unit

    @Override
    public double convertToBaseUnit(
            double value
    ) {

        double convertedValue =
                toCelsius.apply(
                        value
                );

        return Math.round(
                convertedValue * 100.0
        ) / 100.0;
    }

    // UC14 UPDATE
    // Convert Celsius base unit to target unit

    @Override
    public double convertFromBaseUnit(
            double baseValue
    ) {

        double convertedValue =
                fromCelsius.apply(
                        baseValue
                );

        return Math.round(
                convertedValue * 100.0
        ) / 100.0;
    }

//     // UC14 UPDATE
//     // Temperature arithmetic support disabled

//     @Override
//     public boolean supportsArithmetic() {

//         return supportsArithmetic
//                 .isSupported();
//     }

    // UC14 UPDATE
    // Reject arithmetic operations on temperature

    @Override
    public void validateOperationSupport(
            String operation
    ) {

        throw new UnsupportedOperationException(
                "Temperature does not support "
                + operation.toLowerCase()
                + " operation"
        );
    }

        // UC15 UPDATE
    // Returns measurement category

    @Override
    public String getMeasurementType() {

        return "TEMPERATURE";
    }

    // UC15 UPDATE
    // Returns unit instance

    @Override
    public IMeasurable getUnitInstance() {

        return this;
    }

    public static void main(
            String[] args
    ) {

        System.out.println(
                "0 Celsius in Fahrenheit = "
                + FAHRENHEIT.convertFromBaseUnit(
                        0.0
                )
        );

        System.out.println(
                "32 Fahrenheit in Celsius = "
                + FAHRENHEIT.convertToBaseUnit(
                        32.0
                )
        );

        System.out.println(
                "273.15 Kelvin in Celsius = "
                + KELVIN.convertToBaseUnit(
                        273.15
                )
        );
    }
}