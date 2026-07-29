package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.enums.LengthUnit;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.apps.quantitymeasurement.repository.QuantityMeasurementRepository;

// UC16 UPDATE
// Integration tests for service and database repository
@SpringBootTest
class QuantityMeasurementIntegrationTest {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private IQuantityMeasurementService service;

    // UC16 UPDATE
    // Initialize service and database before every test
    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    // UC16 UPDATE
// Verify comparison through service and database integration
@Test
void testCompareIntegration() {

    QuantityDTO oneFoot =
            new QuantityDTO(
                    1,
                    LengthUnit.FEET.name(),
                    "LENGTH"
            );

    QuantityDTO twelveInches =
            new QuantityDTO(
                    12,
                    LengthUnit.INCHES.name(),
                    "LENGTH"
            );

    boolean result =
            service.compare(
                    oneFoot,
                    twelveInches
            );

    assertTrue(result);

    assertEquals(
            1,
            repository.count()
    );
}


// UC16 UPDATE
// Verify conversion through service and database integration
@Test
void testConvertIntegration() {

    QuantityDTO oneFoot =
            new QuantityDTO(
                    1,
                    LengthUnit.FEET.name(),
                    "LENGTH"
            );

    QuantityDTO yard =
        new QuantityDTO(
                0,
                LengthUnit.YARDS.name(),
                "LENGTH"
        );

QuantityDTO result =
        service.convert(
                oneFoot,
                yard
        );

    assertEquals(
            0.33,
            result.getValue(),
            0.01
    );

    assertEquals(
            LengthUnit.YARDS.name(),
            result.getUnit()
    );

    assertEquals(
            1,
            repository.count()
    );
}




}