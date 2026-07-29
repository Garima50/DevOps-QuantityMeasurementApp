package com.apps.quantitymeasurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.dto.request.ArithmeticRequest;
import com.apps.quantitymeasurement.dto.request.CompareRequest;
import com.apps.quantitymeasurement.dto.request.ConvertRequest;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

@RestController
@RequestMapping("/api/quantity")
public class QuantityMeasurementController {

    // UC17 UPDATE
    // Inject service layer

    private final IQuantityMeasurementService service;

    // UC17 UPDATE
    // Constructor injection

    @Autowired
    public QuantityMeasurementController(
            IQuantityMeasurementService service
    ) {
        this.service = service;
    }

    // UC17 UPDATE
    // Compare two quantities

    @PostMapping("/compare")
    public boolean compare(
            @RequestBody CompareRequest request
    ) {

        return service.compare(
                request.getFirstQuantity(),
                request.getSecondQuantity()
        );
    }

    // UC17 UPDATE
    // Convert quantity

    @PostMapping("/convert")
    public QuantityDTO convert(
            @RequestBody ConvertRequest request
    ) {

        return service.convert(
                request.getSourceQuantity(),
                request.getTargetQuantity()
        );
    }

    // UC17 UPDATE
    // Add quantities

    @PostMapping("/add")
    public QuantityDTO add(
            @RequestBody ArithmeticRequest request
    ) {

        if (request.getTargetQuantity() == null) {

            return service.add(
                    request.getFirstQuantity(),
                    request.getSecondQuantity()
            );
        }

        return service.add(
                request.getFirstQuantity(),
                request.getSecondQuantity(),
                request.getTargetQuantity()
        );
    }

    // UC17 UPDATE
    // Subtract quantities

    @PostMapping("/subtract")
    public QuantityDTO subtract(
            @RequestBody ArithmeticRequest request
    ) {

        if (request.getTargetQuantity() == null) {

            return service.subtract(
                    request.getFirstQuantity(),
                    request.getSecondQuantity()
            );
        }

        return service.subtract(
                request.getFirstQuantity(),
                request.getSecondQuantity(),
                request.getTargetQuantity()
        );
    }

    // UC17 UPDATE
    // Divide quantities

    @PostMapping("/divide")
    public double divide(
            @RequestBody ArithmeticRequest request
    ) {

        return service.divide(
                request.getFirstQuantity(),
                request.getSecondQuantity()
        );
    }

    // UC20 UPDATE
    // Return past operations, most recent first

    @GetMapping("/history")
    public List<QuantityMeasurementEntity> getHistory() {

        return service.getHistory();
    }
}