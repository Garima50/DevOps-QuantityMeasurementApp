package com.apps.quantitymeasurement.service;

import java.util.List;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.interfaces.IMeasurable;
import com.apps.quantitymeasurement.enums.LengthUnit;
import com.apps.quantitymeasurement.enums.TemperatureUnit;
import com.apps.quantitymeasurement.enums.VolumeUnit;
import com.apps.quantitymeasurement.enums.WeightUnit;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    // UC15 UPDATE
    // Repository object for persistence

    private final QuantityMeasurementRepository repository;

    // UC15 UPDATE
    // Constructor injection

    public QuantityMeasurementServiceImpl(
            QuantityMeasurementRepository repository
    ) {

        this.repository = repository;
    }

    // UC15 UPDATE
    // Supported arithmetic operations

    private enum ArithmeticOperation {

        ADD,

        SUBTRACT,

        DIVIDE
    }

    @Override
    public boolean compare(QuantityDTO firstQuantity, QuantityDTO secondQuantity) {

        // Convert DTOs into internal model objects
        QuantityModel<IMeasurable> firstModel = getQuantityModel(firstQuantity);
        QuantityModel<IMeasurable> secondModel = getQuantityModel(secondQuantity);

        // Compare both quantities
        boolean result = compare(firstModel, secondModel);

        // Save comparison history
        repository.save(
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "COMPARE",
                        result ? 1.0 : 0.0
                )
        );

        return result;
    }

    // Compare two quantities after converting them to a common unit
    private <U extends IMeasurable> boolean compare(
            QuantityModel<U> firstModel,
            QuantityModel<U> secondModel) {

        // Both quantities must belong to the same measurement type
        if (!firstModel.getUnit().getMeasurementType()
                .equals(secondModel.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException(
                    "Cannot compare different measurement types.");
        }

        // Convert both values to their base unit
        double firstBaseValue = firstModel.getUnit()
                .convertToBaseUnit(firstModel.getValue());

        double secondBaseValue = secondModel.getUnit()
                .convertToBaseUnit(secondModel.getValue());

        // Compare the converted values
        return Math.abs(firstBaseValue - secondBaseValue) < 0.0001;
    }


    @Override
    public QuantityDTO convert(
            QuantityDTO sourceQuantity,
            QuantityDTO targetQuantity
    ) {

        // Convert DTOs into internal model objects
        QuantityModel<IMeasurable> sourceModel =
                getQuantityModel(
                        sourceQuantity
                );

        QuantityModel<IMeasurable> targetModel =
                getQuantityModel(
                        targetQuantity
                );

        // Convert to target unit
        double convertedValue =
                convertTo(
                        sourceModel,
                        targetModel.getUnit()
                );

        QuantityDTO result =
                new QuantityDTO(
                        convertedValue,
                        targetQuantity.getUnit(),
                        targetQuantity.getMeasurementType()
                );

        // UC15 UPDATE
        // Save conversion history

        repository.save(
                new QuantityMeasurementEntity(
                        sourceModel,
                        targetModel,
                        "CONVERT",
                        getQuantityModel(result)
                )
        );

        return result;
    }


// UC15 UPDATE
// Convert quantity to target unit

    private <U extends IMeasurable>
    double convertTo(
            QuantityModel<U> quantity,
            U targetUnit
    ) {

        if (quantity == null
                || targetUnit == null) {

            throw new QuantityMeasurementException(
                    "Quantity or target unit cannot be null."
            );
        }

        // Both units must belong to the same measurement type
        if (!quantity.getUnit()
                .getMeasurementType()
                .equals(targetUnit.getMeasurementType())) {

            throw new QuantityMeasurementException(
                    "Cannot convert between different measurement types."
            );
        }

        // Temperature uses separate conversion formulas
        if (quantity.getUnit() instanceof TemperatureUnit) {

            return convertTemperatureUnit(
                    quantity,
                    (TemperatureUnit) targetUnit
            );
        }

        // Convert through base unit
        double baseValue =
                quantity.getUnit()
                        .convertToBaseUnit(
                                quantity.getValue()
                        );

        return targetUnit.convertFromBaseUnit(
                baseValue
        );
    }


// UC15 UPDATE
// Convert temperature to target unit

    private double convertTemperatureUnit(
            QuantityModel<?> quantity,
            TemperatureUnit targetUnit
    ) {

        TemperatureUnit sourceUnit =
                (TemperatureUnit) quantity.getUnit();

        double value = quantity.getValue();

        if (sourceUnit == targetUnit) {
            return value;
        }

        // Convert source temperature to Celsius
        double celsius;

        switch (sourceUnit) {

            case CELSIUS:
                celsius = value;
                break;

            case FAHRENHEIT:
                celsius = (value - 32) * 5 / 9;
                break;

            case KELVIN:
                celsius = value - 273.15;
                break;

            default:
                throw new QuantityMeasurementException(
                        "Unsupported temperature unit."
                );
        }

        // Convert Celsius to target unit
        switch (targetUnit) {

            case CELSIUS:
                return celsius;

            case FAHRENHEIT:
                return (celsius * 9 / 5) + 32;

            case KELVIN:
                return celsius + 273.15;

            default:
                throw new QuantityMeasurementException(
                        "Unsupported temperature unit."
                );
        }
    }

    @Override
    public QuantityDTO add(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity
    ) {

        // UC15 UPDATE
        // Convert DTOs into model objects

        QuantityModel<IMeasurable> firstModel =
                getQuantityModel(
                        firstQuantity
                );

        QuantityModel<IMeasurable> secondModel =
                getQuantityModel(
                        secondQuantity
                );

        // UC15 UPDATE
        // Validate arithmetic operands

        validateArithmeticOperands(
                firstModel,
                secondModel
        );

        // UC15 UPDATE
        // Perform addition

        double resultValue =
                performArithmetic(
                        firstModel,
                        secondModel,
                        ArithmeticOperation.ADD
                );

        QuantityDTO result =
                new QuantityDTO(
                        resultValue,
                        firstQuantity.getUnit(),
                        firstQuantity.getMeasurementType()
                );

        // UC15 UPDATE
        // Save operation in repository

        repository.save(
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "ADD",
                        getQuantityModel(result)
                )
        );

        return result;
    }

    @Override
    public QuantityDTO add(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity,
            QuantityDTO targetQuantity
    ) {

        // UC15 UPDATE
        // Convert DTOs into model objects

        QuantityModel<IMeasurable> firstModel =
                getQuantityModel(
                        firstQuantity
                );

        QuantityModel<IMeasurable> secondModel =
                getQuantityModel(
                        secondQuantity
                );

        QuantityModel<IMeasurable> targetModel =
                getQuantityModel(
                        targetQuantity
                );

        // UC15 UPDATE
        // Validate arithmetic operands

        validateArithmeticOperands(
                firstModel,
                secondModel
        );

        // UC15 UPDATE
        // Perform addition in target unit

        double resultValue =
                performArithmetic(
                        firstModel,
                        secondModel,
                        targetModel.getUnit(),
                        ArithmeticOperation.ADD
                );

        QuantityDTO result =
                new QuantityDTO(
                        resultValue,
                        targetQuantity.getUnit(),
                        targetQuantity.getMeasurementType()
                );

        // UC15 UPDATE
        // Save operation in repository

        repository.save(
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "ADD",
                        getQuantityModel(result)
                )
        );

        return result;
    }


    @Override
    public QuantityDTO subtract(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity
    ) {

        // UC15 UPDATE
        // Convert DTOs into model objects

        QuantityModel<IMeasurable> firstModel =
                getQuantityModel(
                        firstQuantity
                );

        QuantityModel<IMeasurable> secondModel =
                getQuantityModel(
                        secondQuantity
                );

        // UC15 UPDATE
        // Validate arithmetic operands

        validateArithmeticOperands(
                firstModel,
                secondModel
        );

        // UC15 UPDATE
        // Perform subtraction

        double resultValue =
                performArithmetic(
                        firstModel,
                        secondModel,
                        ArithmeticOperation.SUBTRACT
                );

        QuantityDTO result =
                new QuantityDTO(
                        resultValue,
                        firstQuantity.getUnit(),
                        firstQuantity.getMeasurementType()
                );

        // UC15 UPDATE
        // Save operation in repository

        repository.save(
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "SUBTRACT",
                        getQuantityModel(result)
                )
        );

        return result;
    }



    @Override
    public QuantityDTO subtract(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity,
            QuantityDTO targetQuantity
    ) {

        // UC15 UPDATE
        // Convert DTOs into model objects

        QuantityModel<IMeasurable> firstModel =
                getQuantityModel(
                        firstQuantity
                );

        QuantityModel<IMeasurable> secondModel =
                getQuantityModel(
                        secondQuantity
                );

        QuantityModel<IMeasurable> targetModel =
                getQuantityModel(
                        targetQuantity
                );

        // UC15 UPDATE
        // Validate arithmetic operands

        validateArithmeticOperands(
                firstModel,
                secondModel
        );


        // UC15 UPDATE
        // Perform subtraction in target unit

        double resultValue =
                performArithmetic(
                        firstModel,
                        secondModel,
                        targetModel.getUnit(),
                        ArithmeticOperation.SUBTRACT
                );

        QuantityDTO result =
                new QuantityDTO(
                        resultValue,
                        targetQuantity.getUnit(),
                        targetQuantity.getMeasurementType()
                );

        // UC15 UPDATE
        // Save operation in repository

        repository.save(
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "SUBTRACT",
                        getQuantityModel(result)
                )
        );

        return result;
    }


    @Override
    public double divide(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity
    ) {

        // UC15 UPDATE
        // Convert DTOs into model objects

        QuantityModel<IMeasurable> firstModel =
                getQuantityModel(
                        firstQuantity
                );

        QuantityModel<IMeasurable> secondModel =
                getQuantityModel(
                        secondQuantity
                );

        // UC15 UPDATE
        // Validate arithmetic operands

        validateArithmeticOperands(
                firstModel,
                secondModel
        );

        // UC15 UPDATE
        // Perform division

        double result =
                performArithmetic(
                        firstModel,
                        secondModel,
                        ArithmeticOperation.DIVIDE
                );

        // UC15 UPDATE
        // Save operation in repository

        repository.save(
                new QuantityMeasurementEntity(
                        firstModel,
                        secondModel,
                        "DIVIDE",
                        result
                )
        );

        return result;
    }

// UC15 UPDATE
// Convert DTO into internal quantity model

    private QuantityModel<IMeasurable> getQuantityModel(
            QuantityDTO quantityDTO
    ) {

        if (quantityDTO == null) {

            throw new QuantityMeasurementException(
                    "Quantity cannot be null."
            );
        }

        IMeasurable unit;

        switch (quantityDTO.getMeasurementType().toUpperCase()) {

            case "LENGTH":

                unit = LengthUnit.valueOf(
                        quantityDTO.getUnit().toUpperCase()
                );
                break;

            case "WEIGHT":

                unit = WeightUnit.valueOf(
                        quantityDTO.getUnit().toUpperCase()
                );
                break;

            case "VOLUME":

                unit = VolumeUnit.valueOf(
                        quantityDTO.getUnit().toUpperCase()
                );
                break;

            case "TEMPERATURE":

                unit = TemperatureUnit.valueOf(
                        quantityDTO.getUnit().toUpperCase()
                );
                break;

            default:

                throw new QuantityMeasurementException(
                        "Unsupported measurement type."
                );
        }

        return new QuantityModel<>(
                quantityDTO.getValue(),
                unit
        );
    }

// UC15 UPDATE
// Validate arithmetic operands

    private <U extends IMeasurable>
    void validateArithmeticOperands(
            QuantityModel<U> firstQuantity,
            QuantityModel<U> secondQuantity
    ) {

        if (
                firstQuantity == null
                        || secondQuantity == null
        ) {

            throw new QuantityMeasurementException(
                    "Quantities cannot be null."
            );
        }

        // Both quantities must belong to the same measurement category
        if (
                !firstQuantity.getUnit()
                        .getMeasurementType()
                        .equals(
                                secondQuantity.getUnit()
                                        .getMeasurementType()
                        )
        ) {

            throw new QuantityMeasurementException(
                    "Arithmetic operations are allowed only within the same measurement type."
            );
        }

        // Temperature does not support arithmetic operations
        if (
                firstQuantity.getUnit() instanceof TemperatureUnit
                        || secondQuantity.getUnit() instanceof TemperatureUnit
        ) {

            throw new QuantityMeasurementException(
                    "Arithmetic operations are not supported for temperature."
            );
        }
    }


// UC15 UPDATE
// Perform arithmetic operation

    private <U extends IMeasurable>
    double performArithmetic(
            QuantityModel<U> firstQuantity,
            QuantityModel<U> secondQuantity,
            ArithmeticOperation operation
    ) {

        double firstBaseValue =
                firstQuantity.getUnit()
                        .convertToBaseUnit(
                                firstQuantity.getValue()
                        );

        double secondBaseValue =
                secondQuantity.getUnit()
                        .convertToBaseUnit(
                                secondQuantity.getValue()
                        );

        switch (operation) {

            case ADD: {

                double result =
                        firstBaseValue +
                                secondBaseValue;

                return firstQuantity.getUnit()
                        .convertFromBaseUnit(
                                result
                        );
            }

            case SUBTRACT: {

                double result =
                        firstBaseValue -
                                secondBaseValue;

                return firstQuantity.getUnit()
                        .convertFromBaseUnit(
                                result
                        );
            }

            case DIVIDE: {

                if (
                        Double.compare(
                                secondBaseValue,
                                0.0
                        ) == 0
                ) {

                    throw new QuantityMeasurementException(
                            "Cannot divide by zero."
                    );
                }

                return firstBaseValue /
                        secondBaseValue;
            }

            default:

                throw new QuantityMeasurementException(
                        "Unsupported arithmetic operation."
                );
        }
    }


// UC15 UPDATE
// Perform arithmetic operation in target unit

    private double performArithmetic(
            QuantityModel<IMeasurable> firstQuantity,
            QuantityModel<IMeasurable> secondQuantity,
            IMeasurable targetUnit,
            ArithmeticOperation operation
    ) {

        double firstBaseValue =
                firstQuantity.getUnit()
                        .convertToBaseUnit(
                                firstQuantity.getValue()
                        );

        double secondBaseValue =
                secondQuantity.getUnit()
                        .convertToBaseUnit(
                                secondQuantity.getValue()
                        );

        switch (operation) {

            case ADD: {

                double result =
                        firstBaseValue +
                                secondBaseValue;

                return targetUnit
                        .convertFromBaseUnit(
                                result
                        );
            }

            case SUBTRACT: {

                double result =
                        firstBaseValue -
                                secondBaseValue;

                return targetUnit
                        .convertFromBaseUnit(
                                result
                        );
            }

            case DIVIDE: {

                if (
                        Double.compare(
                                secondBaseValue,
                                0.0
                        ) == 0
                ) {

                    throw new QuantityMeasurementException(
                            "Cannot divide by zero."
                    );
                }

                return firstBaseValue /
                        secondBaseValue;
            }

            default:

                throw new QuantityMeasurementException(
                        "Unsupported arithmetic operation."
                );
        }
    }

// UC20 UPDATE
// Return past operations, most recent first

    @Override
    public List<QuantityMeasurementEntity> getHistory() {
        return repository.findAllByOrderByIdDesc();
    }

}