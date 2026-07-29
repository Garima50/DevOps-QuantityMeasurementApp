package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.enums.LengthUnit;
import com.apps.quantitymeasurement.enums.TemperatureUnit;
import com.apps.quantitymeasurement.enums.VolumeUnit;
import com.apps.quantitymeasurement.enums.WeightUnit;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
//import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
//import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuantityMeasurementServiceTest {

    @Autowired
    private IQuantityMeasurementService service;

    @Test
void shouldCompareOneFootAndTwelveInches() {

    QuantityDTO foot =
            new QuantityDTO(
                    1,
                    LengthUnit.FEET.name(),
                    "LENGTH"
            );

    QuantityDTO inches =
            new QuantityDTO(
                    12,
                    LengthUnit.INCHES.name(),
                    "LENGTH"
            );

    assertTrue(
            service.compare(
                    foot,
                    inches
            )
    );
}


@Test
void shouldCompareOneKilogramAndThousandGram() {

    QuantityDTO kilogram =
            new QuantityDTO(
                    1,
                    WeightUnit.KILOGRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO gram =
            new QuantityDTO(
                    1000,
                    WeightUnit.GRAM.name(),
                    "WEIGHT"
            );

    assertTrue(
            service.compare(
                    kilogram,
                    gram
            )
    );
}

@Test
void shouldCompareOneGallonAndThreePointSevenEightLitre() {

    QuantityDTO gallon =
            new QuantityDTO(
                    1,
                    VolumeUnit.GALLON.name(),
                    "VOLUME"
            );

    QuantityDTO litre =
            new QuantityDTO(
                    3.78541,
                    VolumeUnit.LITRE.name(),
                    "VOLUME"
            );

    assertTrue(
            service.compare(
                    gallon,
                    litre
            )
    );
}

@Test
void shouldConvertOneFootToTwelveInches() {

    QuantityDTO source =
            new QuantityDTO(
                    1,
                    LengthUnit.FEET.name(),
                    "LENGTH"
            );

    QuantityDTO target =
            new QuantityDTO(
                    0,
                    LengthUnit.INCHES.name(),
                    "LENGTH"
            );

    QuantityDTO result =
            service.convert(
                    source,
                    target
            );

    assertEquals(
            12,
            result.getValue()
    );

    assertEquals(
            LengthUnit.INCHES.name(),
            result.getUnit()
    );
}

@Test
void shouldConvertTwelveInchesToOneFoot() {

    QuantityDTO source =
            new QuantityDTO(
                    12,
                    LengthUnit.INCHES.name(),
                    "LENGTH"
            );

    QuantityDTO target =
            new QuantityDTO(
                    0,
                    LengthUnit.FEET.name(),
                    "LENGTH"
            );

    QuantityDTO result =
            service.convert(
                    source,
                    target
            );

    assertEquals(
            1,
            result.getValue()
    );

    assertEquals(
            LengthUnit.FEET.name(),
            result.getUnit()
    );
}

@Test
void shouldConvertOneKilogramToThousandGram() {

    QuantityDTO source =
            new QuantityDTO(
                    1,
                    WeightUnit.KILOGRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO target =
            new QuantityDTO(
                    0,
                    WeightUnit.GRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO result =
            service.convert(
                    source,
                    target
            );

    assertEquals(
            1000,
            result.getValue()
    );

    assertEquals(
            WeightUnit.GRAM.name(),
            result.getUnit()
    );
}

@Test
void shouldConvertThousandGramToOneKilogram() {

    QuantityDTO source =
            new QuantityDTO(
                    1000,
                    WeightUnit.GRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO target =
            new QuantityDTO(
                    0,
                    WeightUnit.KILOGRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO result =
            service.convert(
                    source,
                    target
            );

    assertEquals(
            1,
            result.getValue()
    );

    assertEquals(
            WeightUnit.KILOGRAM.name(),
            result.getUnit()
    );
}

@Test
void shouldConvertOneGallonToThreePointSevenEightFiveFourOneLitre() {

    QuantityDTO source =
            new QuantityDTO(
                    1,
                    VolumeUnit.GALLON.name(),
                    "VOLUME"
            );

    QuantityDTO target =
            new QuantityDTO(
                    0,
                    VolumeUnit.LITRE.name(),
                    "VOLUME"
            );

    QuantityDTO result =
            service.convert(
                    source,
                    target
            );

    assertEquals(
            3.78541,
            result.getValue(),
            0.01
    );
}

@Test
void shouldConvertZeroCelsiusToThirtyTwoFahrenheit() {

    QuantityDTO source =
            new QuantityDTO(
                    0,
                    TemperatureUnit.CELSIUS.name(),
                    "TEMPERATURE"
            );

    QuantityDTO target =
            new QuantityDTO(
                    0,
                    TemperatureUnit.FAHRENHEIT.name(),
                    "TEMPERATURE"
            );

    QuantityDTO result =
            service.convert(
                    source,
                    target
            );

    assertEquals(
            32,
            result.getValue(),
            0.01
    );
}


@Test
void shouldAddOneFootAndTwoInches() {

    QuantityDTO first =
            new QuantityDTO(
                    1,
                    LengthUnit.FEET.name(),
                    "LENGTH"
            );

    QuantityDTO second =
            new QuantityDTO(
                    2,
                    LengthUnit.INCHES.name(),
                    "LENGTH"
            );

    QuantityDTO result =
            service.add(
                    first,
                    second
            );

    assertEquals(
            1.17,
            result.getValue(),
            0.01
    );
}

@Test
void shouldAddOneKilogramAndFiveHundredGram() {

    QuantityDTO first =
            new QuantityDTO(
                    1,
                    WeightUnit.KILOGRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO second =
            new QuantityDTO(
                    500,
                    WeightUnit.GRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO result =
            service.add(
                    first,
                    second
            );

    assertEquals(
            1.5,
            result.getValue(),
            0.01
    );
}


@Test
void shouldSubtractFiveHundredGramFromOneKilogram() {

    QuantityDTO first =
            new QuantityDTO(
                    1,
                    WeightUnit.KILOGRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO second =
            new QuantityDTO(
                    500,
                    WeightUnit.GRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO result =
            service.subtract(
                    first,
                    second
            );

    assertEquals(
            0.5,
            result.getValue(),
            0.01
    );
}

@Test
void shouldDivideOneKilogramByFiveHundredGram() {

    QuantityDTO first =
            new QuantityDTO(
                    1,
                    WeightUnit.KILOGRAM.name(),
                    "WEIGHT"
            );

    QuantityDTO second =
            new QuantityDTO(
                    500,
                    WeightUnit.GRAM.name(),
                    "WEIGHT"
            );

    double result =
            service.divide(
                    first,
                    second
            );

    assertEquals(
            2.0,
            result,
            0.01
    );
}

@Test
void shouldThrowExceptionWhenAddingTemperature() {

    QuantityDTO first =
            new QuantityDTO(
                    10,
                    TemperatureUnit.CELSIUS.name(),
                    "TEMPERATURE"
            );

    QuantityDTO second =
            new QuantityDTO(
                    20,
                    TemperatureUnit.CELSIUS.name(),
                    "TEMPERATURE"
            );

    assertThrows(
            QuantityMeasurementException.class,
            () -> service.add(
                    first,
                    second
            )
    );
}





}