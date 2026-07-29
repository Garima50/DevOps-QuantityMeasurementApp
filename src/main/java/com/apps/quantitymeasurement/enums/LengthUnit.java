package com.apps.quantitymeasurement.enums;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

public enum LengthUnit implements IMeasurable {

    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(0.393701);

    private final double conversionFactor;

    LengthUnit(
            double conversionFactor
    ) {

        this.conversionFactor =
                conversionFactor;
    }

    @Override
    public double getConversionFactor() {

        return conversionFactor;
    }

    // UC10 UPDATE
    // Implement IMeasurable contract for length units

    @Override
    public double convertToBaseUnit(
            double value
    ) {

        double baseValue =
                value * conversionFactor;

        return Math.round(
                baseValue * 100.0
        ) / 100.0;
    }

    // UC10 UPDATE
    // Implement IMeasurable contract for length units

    @Override
    public double convertFromBaseUnit(
            double baseValue
    ) {

        double convertedValue =
                baseValue / conversionFactor;

        return Math.round(
                convertedValue * 100.0
        ) / 100.0;
    }

     // UC15 UPDATE
    // Returns measurement category

    @Override
    public String getMeasurementType() {

        return "LENGTH";
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
                "1 FOOT in inches = "
                + FEET.convertToBaseUnit(
                        1.0
                )
        );

        System.out.println(
                "12 INCHES in feet = "
                + FEET.convertFromBaseUnit(
                        12.0
                )
        );
    }
}