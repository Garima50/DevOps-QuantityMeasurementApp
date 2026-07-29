package com.apps.quantitymeasurement.enums;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

public enum VolumeUnit implements IMeasurable {

    // UC11 UPDATE
    // Implement IMeasurable for generic quantity support

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactor;

    VolumeUnit(
            double conversionFactor
    ) {

        this.conversionFactor =
                conversionFactor;
    }

    @Override
    public double getConversionFactor() {

        return conversionFactor;
    }

    // UC11 UPDATE
    // Implement IMeasurable contract for volume units

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

    // UC11 UPDATE
    // Implement IMeasurable contract for volume units

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

        return "VOLUME";
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
                "1 LITRE in millilitres = "
                + MILLILITRE.convertFromBaseUnit(
                        1.0
                )
        );

        System.out.println(
                "1 GALLON in litres = "
                + GALLON.convertToBaseUnit(
                        1.0
                )
        );
    }
}