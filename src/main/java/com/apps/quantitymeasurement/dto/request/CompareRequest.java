package com.apps.quantitymeasurement.dto.request;

import com.apps.quantitymeasurement.dto.QuantityDTO;

public class CompareRequest {

    // UC17 UPDATE
    // First quantity for comparison

    private QuantityDTO firstQuantity;

    // UC17 UPDATE
    // Second quantity for comparison

    private QuantityDTO secondQuantity;

    // UC17 UPDATE
    // Default constructor

    public CompareRequest() {
    }

    // UC17 UPDATE
    // Parameterized constructor

    public CompareRequest(
            QuantityDTO firstQuantity,
            QuantityDTO secondQuantity
    ) {

        this.firstQuantity = firstQuantity;
        this.secondQuantity = secondQuantity;
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
}