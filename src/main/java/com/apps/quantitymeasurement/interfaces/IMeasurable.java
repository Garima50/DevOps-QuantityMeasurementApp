package com.apps.quantitymeasurement.interfaces;

@FunctionalInterface
interface SupportsArithmetic {

    boolean isSupported();
}

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    // UC14
    default boolean supportsArithmetic() {
        return true;
    }

    // UC14
    default void validateOperationSupport(String operation) {
        // subclasses may override
    }

    // UC15
    default String getMeasurementType() {
        return getClass().getSimpleName();
    }

    // UC15
    default IMeasurable getUnitInstance() {
        return this;
    }
}
