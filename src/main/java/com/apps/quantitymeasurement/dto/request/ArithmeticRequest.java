package com.apps.quantitymeasurement.dto.request;

import com.apps.quantitymeasurement.dto.QuantityDTO;

public class ArithmeticRequest {

    // UC17 UPDATE
    // First quantity

    private QuantityDTO firstQuantity;

    // UC17 UPDATE
    // Second quantity

    private QuantityDTO secondQuantity;

    // UC17 UPDATE
    // Optional target quantity

    private QuantityDTO targetQuantity;

    // UC17 UPDATE
    // Default constructor

    public ArithmeticRequest() {
    }

    // UC17 UPDATE
    // Parameterized constructor

    public ArithmeticRequest(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity,
            QuantityDTO targetQuantity
    ) {

        this.firstQuantity = firstQuantity;
        this.secondQuantity = secondQuantity;
        this.targetQuantity = targetQuantity;
    }

    public QuantityDTO getFirstQuantity() {
        return firstQuantity;
    }

    public void setFirstQuantity(
            QuantityDTO firstQuantity
    ) {
        this.firstQuantity = firstQuantity;
    }

    public QuantityDTO getSecondQuantity() {
        return secondQuantity;
    }

    public void setSecondQuantity(
            QuantityDTO secondQuantity
    ) {
        this.secondQuantity = secondQuantity;
    }

    public QuantityDTO getTargetQuantity() {
        return targetQuantity;
    }

    public void setTargetQuantity(
            QuantityDTO targetQuantity
    ) {
        this.targetQuantity = targetQuantity;
    }
}