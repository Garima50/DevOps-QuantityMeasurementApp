package com.apps.quantitymeasurement.exception;

/**
 * Custom exception for the Quantity Measurement Application.
 *
 * This exception is thrown whenever an invalid quantity measurement
 * operation or repository operation occurs.
 *
 * UC15 UPDATE
 * Introduces a custom exception for the Service and Repository layers.
 */
public class QuantityMeasurementException
        extends RuntimeException {

    /**
     * Creates an exception with the specified message.
     *
     * @param message exception message
     */
    public QuantityMeasurementException(
            String message
    ) {

        super(
                message
        );
    }

    /**
     * Creates an exception with the specified message and cause.
     *
     * @param message exception message
     * @param cause underlying exception
     */
    public QuantityMeasurementException(
            String message,
            Throwable cause
    ) {

        super(
                message,
                cause
        );
    }

    /**
     * Main method for testing.
     */
    public static void main(
            String[] args
    ) {

        try {

            throw new QuantityMeasurementException(
                    "Sample Quantity Measurement Exception"
            );

        }
        catch (
                QuantityMeasurementException exception
        ) {

            System.out.println(
                    exception.getMessage()
            );
        }
    }
}