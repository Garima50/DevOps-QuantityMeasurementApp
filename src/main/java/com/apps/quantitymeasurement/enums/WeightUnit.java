package com.apps.quantitymeasurement.enums;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

public enum WeightUnit implements IMeasurable {

    // UC10 UPDATE
    // Implement IMeasurable for generic quantity support

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactor;

    WeightUnit(
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
    // Implement IMeasurable contract for weight units

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
    // Implement IMeasurable contract for weight units

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

        return "WEIGHT";
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
                "1 KG in grams = "
                + GRAM.convertFromBaseUnit(
                        1.0
                )
        );

        System.out.println(
                "1000 G in kg = "
                + GRAM.convertToBaseUnit(
                        1000.0
                )
        );
    }
}