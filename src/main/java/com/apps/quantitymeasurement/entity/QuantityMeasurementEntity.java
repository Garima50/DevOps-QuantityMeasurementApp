package com.apps.quantitymeasurement.entity;

import java.io.Serializable;
import java.util.Objects;

import com.apps.quantitymeasurement.model.QuantityModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quantity_measurements")
public class QuantityMeasurementEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_value")
    private Double firstValue;

    @Column(name = "first_unit")
    private String firstUnit;

    @Column(name = "first_measurement_type")
    private String firstMeasurementType;

    @Column(name = "second_value")
    private Double secondValue;

    @Column(name = "second_unit")
    private String secondUnit;

    @Column(name = "second_measurement_type")
    private String secondMeasurementType;

    @Column(name = "operation")
    private String operation;

    @Column(name = "result_value")
    private Double resultValue;

    @Column(name = "result_unit")
    private String resultUnit;

    @Column(name = "result_measurement_type")
    private String resultMeasurementType;

    @Column(name = "numeric_result")
    private Double numericResult;

    @Column(name = "error_message")
    private String errorMessage;

    public QuantityMeasurementEntity() {
    }

    // Add / Subtract / Convert
    public QuantityMeasurementEntity(
            QuantityModel<?> thisQuantity,
            QuantityModel<?> thatQuantity,
            String operation,
            QuantityModel<?> quantityResult
    ) {

        this.firstValue = thisQuantity.getValue();
        this.firstUnit = thisQuantity.getUnit().toString();
        this.firstMeasurementType =
                thisQuantity.getUnit().getMeasurementType();

        this.secondValue = thatQuantity.getValue();
        this.secondUnit = thatQuantity.getUnit().toString();
        this.secondMeasurementType =
                thatQuantity.getUnit().getMeasurementType();

        this.operation = operation;

        this.resultValue = quantityResult.getValue();
        this.resultUnit = quantityResult.getUnit().toString();
        this.resultMeasurementType =
                quantityResult.getUnit().getMeasurementType();
    }

    // Compare / Divide
    public QuantityMeasurementEntity(
            QuantityModel<?> thisQuantity,
            QuantityModel<?> thatQuantity,
            String operation,
            Double numericResult
    ) {

        this.firstValue = thisQuantity.getValue();
        this.firstUnit = thisQuantity.getUnit().toString();
        this.firstMeasurementType =
                thisQuantity.getUnit().getMeasurementType();

        this.secondValue = thatQuantity.getValue();
        this.secondUnit = thatQuantity.getUnit().toString();
        this.secondMeasurementType =
                thatQuantity.getUnit().getMeasurementType();

        this.operation = operation;
        this.numericResult = numericResult;
    }

    // Error case
    public QuantityMeasurementEntity(
            QuantityModel<?> thisQuantity,
            QuantityModel<?> thatQuantity,
            String operation,
            String errorMessage,
            boolean error
    ) {

        this.firstValue = thisQuantity.getValue();
        this.firstUnit = thisQuantity.getUnit().toString();
        this.firstMeasurementType =
                thisQuantity.getUnit().getMeasurementType();

        this.secondValue = thatQuantity.getValue();
        this.secondUnit = thatQuantity.getUnit().toString();
        this.secondMeasurementType =
                thatQuantity.getUnit().getMeasurementType();

        this.operation = operation;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public Double getFirstValue() {
        return firstValue;
    }

    public String getFirstUnit() {
        return firstUnit;
    }

    public String getFirstMeasurementType() {
        return firstMeasurementType;
    }

    public Double getSecondValue() {
        return secondValue;
    }

    public String getSecondUnit() {
        return secondUnit;
    }

    public String getSecondMeasurementType() {
        return secondMeasurementType;
    }

    public String getOperation() {
        return operation;
    }

    public Double getResultValue() {
        return resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public Double getNumericResult() {
        return numericResult;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof QuantityMeasurementEntity))
            return false;

        QuantityMeasurementEntity other =
                (QuantityMeasurementEntity) obj;

        return Objects.equals(firstValue, other.firstValue)
                && Objects.equals(firstUnit, other.firstUnit)
                && Objects.equals(firstMeasurementType, other.firstMeasurementType)
                && Objects.equals(secondValue, other.secondValue)
                && Objects.equals(secondUnit, other.secondUnit)
                && Objects.equals(secondMeasurementType, other.secondMeasurementType)
                && Objects.equals(operation, other.operation)
                && Objects.equals(resultValue, other.resultValue)
                && Objects.equals(resultUnit, other.resultUnit)
                && Objects.equals(resultMeasurementType, other.resultMeasurementType)
                && Objects.equals(numericResult, other.numericResult)
                && Objects.equals(errorMessage, other.errorMessage);
    }

    @Override
    public String toString() {

        if (errorMessage != null) {
            return "Operation : "
                    + operation
                    + ", Error : "
                    + errorMessage;
        }

        if (resultValue != null) {
            return "Operation : "
                    + operation
                    + ", Result : "
                    + resultValue
                    + " "
                    + resultUnit;
        }

        return "Operation : "
                + operation
                + ", Result : "
                + numericResult;
    }
}