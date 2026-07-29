package com.apps.quantitymeasurement.model;

import java.io.Serializable;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

public class Quantity<U extends IMeasurable> implements Serializable {

    // UC10 UPDATE
    // Generic quantity supporting all measurable categories

    private final double value;
    private final U unit;

    // UC13 UPDATE
    // Centralized arithmetic operation dispatch

    private enum ArithmeticOperation {

        ADD {
            @Override
            double compute(
                    double left,
                    double right
            ) {
                return left + right;
            }
        },

        SUBTRACT {
            @Override
            double compute(
                    double left,
                    double right
            ) {
                return left - right;
            }
        },

        DIVIDE {
            @Override
            double compute(
                    double left,
                    double right
            ) {

                if (
                        Double.compare(
                                right,
                                0.0
                        ) == 0
                ) {

                    throw new ArithmeticException(
                            "Cannot divide by zero"
                    );
                }

                return left / right;
            }
        };

        abstract double compute(
                double left,
                double right
        );
    }

    public Quantity(
            double value,
            U unit
    ) {

        if (unit == null) {

            throw new IllegalArgumentException(
                    "Unit cannot be null"
            );
        }

        if (!Double.isFinite(value)) {

            throw new IllegalArgumentException(
                    "Invalid numeric value"
            );
        }

        this.value = value;
        this.unit = unit;
    }

    // UC10 UPDATE
    // Generic conversion through IMeasurable

    private double convertToBaseUnit() {

        return unit.convertToBaseUnit(
                value
        );
    }

    // UC13 UPDATE
    // Centralized validation for arithmetic operations

    private void validateArithmeticOperands(
            Quantity<U> quantity,
            U targetUnit,
            boolean targetUnitRequired
    ) {

        if (quantity == null) {

            throw new IllegalArgumentException(
                    "Quantity cannot be null"
            );
        }

        if (
                this.unit.getClass()
                        != quantity.unit.getClass()
        ) {

            throw new IllegalArgumentException(
                    "Incompatible quantity types"
            );
        }

        if (
                !Double.isFinite(this.value)
                || !Double.isFinite(quantity.value)
        ) {

            throw new IllegalArgumentException(
                    "Invalid numeric value"
            );
        }

        if (
                targetUnitRequired
                && targetUnit == null
        ) {

            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }
    }
    
// UC14 UPDATE
// Validate arithmetic support before operation execution

private void validateOperationSupport(
        ArithmeticOperation operation
) {

    this.unit
            .validateOperationSupport(
                    operation.name()
            );
}

    // UC13 UPDATE
    // Centralized base-unit arithmetic logic

    private double performBaseArithmetic(
            Quantity<U> quantity,
            ArithmeticOperation operation
    ) {

        double leftValue =
                this.convertToBaseUnit();

        double rightValue =
                quantity.convertToBaseUnit();

        return operation.compute(
                leftValue,
                rightValue
        );
    }

    private boolean compare(
            Quantity<?> thatQuantity
    ) {

        return Double.compare(
                this.convertToBaseUnit(),
                thatQuantity.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public boolean equals(
            Object o
    ) {

        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        Quantity<?> that =
                (Quantity<?>) o;

        // UC10 UPDATE
        // Prevent cross-category comparison

        if (
                this.unit.getClass()
                        != that.unit.getClass()
        ) {

            return false;
        }

        return this.compare(
                that
        );
    }

    @Override
    public int hashCode() {

        return Double.hashCode(
                convertToBaseUnit()
        );
    }

    // UC10 UPDATE
    // Generic conversion method

    public Quantity<U> convertTo(
            U targetUnit
    ) {

        if (targetUnit == null) {

            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }

        // UC14 UPDATE
      // Supports linear and temperature conversions

       double baseValue =
        convertToBaseUnit();

       double convertedValue =
        targetUnit.convertFromBaseUnit(
                baseValue
        );

       return new Quantity<>(
        convertedValue,
        targetUnit
);
    }

    // UC13 UPDATE
    // Addition delegates to centralized helper

    public Quantity<U> add(
            Quantity<U> quantity
    ) {

        validateArithmeticOperands(
                quantity,
                null,
                false
        );

        validateOperationSupport(
        ArithmeticOperation.ADD
        
        );

        double resultInBaseUnit =
                performBaseArithmetic(
                        quantity,
                        ArithmeticOperation.ADD
                );

        double convertedResult =
                unit.convertFromBaseUnit(
                        resultInBaseUnit
                );

        return new Quantity<>(
                convertedResult,
                unit
        );
    }

    // UC13 UPDATE
    // Addition with explicit target unit

    public Quantity<U> add(
            Quantity<U> quantity,
            U targetUnit
    ) {

        validateArithmeticOperands(
                quantity,
                targetUnit,
                true
        );

        validateOperationSupport(
        ArithmeticOperation.ADD
        
        );

        double resultInBaseUnit =
                performBaseArithmetic(
                        quantity,
                        ArithmeticOperation.ADD
                );

        double convertedResult =
                targetUnit.convertFromBaseUnit(
                        resultInBaseUnit
                );

        return new Quantity<>(
                convertedResult,
                targetUnit
        );
    }

    // UC13 UPDATE
    // Subtraction delegates to centralized helper

    public Quantity<U> subtract(
            Quantity<U> quantity
    ) {

        validateArithmeticOperands(
                quantity,
                null,
                false
        );

        validateOperationSupport(
        ArithmeticOperation.SUBTRACT

        );

        double resultInBaseUnit =
                performBaseArithmetic(
                        quantity,
                        ArithmeticOperation.SUBTRACT
                );

        double convertedResult =
                unit.convertFromBaseUnit(
                        resultInBaseUnit
                );

        return new Quantity<>(
                convertedResult,
                unit
        );
    }

    // UC13 UPDATE
    // Subtraction with explicit target unit

    public Quantity<U> subtract(
            Quantity<U> quantity,
            U targetUnit
    ) {

        validateArithmeticOperands(
                quantity,
                targetUnit,
                true
        );


        validateOperationSupport(
        ArithmeticOperation.SUBTRACT

        );

        double resultInBaseUnit =
                performBaseArithmetic(
                        quantity,
                        ArithmeticOperation.SUBTRACT
                );

        double convertedResult =
                targetUnit.convertFromBaseUnit(
                        resultInBaseUnit
                );

        return new Quantity<>(
                convertedResult,
                targetUnit
        );
    }

    // UC13 UPDATE
    // Division delegates to centralized helper

    public double divide(
            Quantity<U> quantity
    ) {

        validateArithmeticOperands(
                quantity,
                null,
                false
        );

        validateOperationSupport(
        ArithmeticOperation.DIVIDE
);

        return performBaseArithmetic(
                quantity,
                ArithmeticOperation.DIVIDE
        );
    }

    @Override
    public String toString() {

        return String.format(
                "%.2f %s",
                value,
                unit
        );
    }
}