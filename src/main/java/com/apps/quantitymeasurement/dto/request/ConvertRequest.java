package com.apps.quantitymeasurement.dto.request;

import com.apps.quantitymeasurement.dto.QuantityDTO;

public class ConvertRequest {

    // UC17 UPDATE
    // Source quantity

    private QuantityDTO sourceQuantity;

    // UC17 UPDATE
    // Target quantity

    private QuantityDTO targetQuantity;

    // UC17 UPDATE
    // Default constructor

    public ConvertRequest() {
    }

    // UC17 UPDATE
    // Parameterized constructor

    public ConvertRequest(
            QuantityDTO sourceQuantity,
            QuantityDTO targetQuantity
    ) {

        this.sourceQuantity = sourceQuantity;
        this.targetQuantity = targetQuantity;
    }

    public QuantityDTO getSourceQuantity() {
        return sourceQuantity;
    }

    public void setSourceQuantity(
            QuantityDTO sourceQuantity
    ) {
        this.sourceQuantity = sourceQuantity;
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