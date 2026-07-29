package com.apps.quantitymeasurement.model;

import java.io.Serializable;

import com.apps.quantitymeasurement.interfaces.IMeasurable;

public class QuantityModel<U extends IMeasurable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private double value;
    private U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public U getUnit() {
        return unit;
    }

    public void setUnit(U unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "QuantityModel{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}