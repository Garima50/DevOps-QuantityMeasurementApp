package com.apps.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.enums.LengthUnit;
import com.apps.quantitymeasurement.model.QuantityModel;

@SpringBootTest
class QuantityMeasurementDatabaseRepositoryTest {

    @Autowired
    private QuantityMeasurementRepository repository;

     // UC16 UPDATE
    // Initialize database repository before every test
     @BeforeEach
     void setUp() {
         repository.deleteAll();
     }

    // UC16 UPDATE
// Test saving a measurement in the database
@Test
void testSave() {

    QuantityModel<LengthUnit> oneFoot =
        new QuantityModel<>(
                1.0,
                LengthUnit.FEET
        );

QuantityModel<LengthUnit> twelveInches =
        new QuantityModel<>(
                12.0,
                LengthUnit.INCHES
        );

QuantityMeasurementEntity entity =
        new QuantityMeasurementEntity(
                oneFoot,
                twelveInches,
                "COMPARE",
                1.0
        );

repository.save(entity);

assertEquals(
        1,
        repository.count()
);

}


// UC16 UPDATE
// Test fetching all saved measurements
@Test
void testGetAllMeasurements() {

    QuantityModel<LengthUnit> oneFoot =
            new QuantityModel<>(
                    1.0,
                    LengthUnit.FEET
            );

    QuantityModel<LengthUnit> twelveInches =
            new QuantityModel<>(
                    12.0,
                    LengthUnit.INCHES
            );

    QuantityMeasurementEntity entity =
            new QuantityMeasurementEntity(
                    oneFoot,
                    twelveInches,
                    "COMPARE",
                    1.0
            );

    repository.save(entity);

    List<QuantityMeasurementEntity> measurements =
            repository.findAll();

    assertEquals(
            1,
            measurements.size()
    );
}

}