package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.enums.LengthUnit;
import com.apps.quantitymeasurement.enums.TemperatureUnit;
import com.apps.quantitymeasurement.enums.VolumeUnit;
import com.apps.quantitymeasurement.enums.WeightUnit;
import com.apps.quantitymeasurement.interfaces.IMeasurable;
import com.apps.quantitymeasurement.model.Quantity;
import com.apps.quantitymeasurement.repository.QuantityMeasurementRepository;
//import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
//import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ConnectionPool;

public class QuantityMeasurementApp {

    // UC10 UPDATE
    // Generic equality demonstration

    public static <U extends IMeasurable>
    boolean demonstrateEquality(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        return quantity1.equals(
                quantity2
        );
    }

    // UC10 UPDATE
    // Generic comparison demonstration

    public static <U extends IMeasurable>
    boolean demonstrateComparison(
            double value1,
            U unit1,
            double value2,
            U unit2
    ) {

        Quantity<U> quantity1 =
                new Quantity<>(
                        value1,
                        unit1
                );

        Quantity<U> quantity2 =
                new Quantity<>(
                        value2,
                        unit2
                );

        boolean result =
                demonstrateEquality(
                        quantity1,
                        quantity2
                );

        if (result) {

            System.out.println(
                    "The two measurements are equal."
            );
        }
        else {

            System.out.println(
                    "The two measurements are not equal."
            );
        }

        return result;
    }

    // UC10 UPDATE
    // Generic conversion demonstration

    public static <U extends IMeasurable>
    Quantity<U> demonstrateConversion(
            double value,
            U fromUnit,
            U toUnit
    ) {

        Quantity<U> quantity =
                new Quantity<>(
                        value,
                        fromUnit
                );

        return quantity.convertTo(
                toUnit
        );
    }

    // UC10 UPDATE
    // Overloaded generic conversion

    public static <U extends IMeasurable>
    Quantity<U> demonstrateConversion(
            Quantity<U> quantity,
            U toUnit
    ) {

        return quantity.convertTo(
                toUnit
        );
    }

    // UC10 UPDATE
    // Generic addition demonstration

    public static <U extends IMeasurable>
    Quantity<U> demonstrateAddition(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        if (
                quantity1 == null
                || quantity2 == null
        ) {

            throw new IllegalArgumentException(
                    "Quantities cannot be null"
            );
        }

        return quantity1.add(
                quantity2
        );
    }

    // UC10 UPDATE
    // Generic addition with target unit

    public static <U extends IMeasurable>
    Quantity<U> demonstrateAddition(
            Quantity<U> quantity1,
            Quantity<U> quantity2,
            U targetUnit
    ) {

        if (
                quantity1 == null
                || quantity2 == null
        ) {

            throw new IllegalArgumentException(
                    "Quantities cannot be null"
            );
        }

        if (targetUnit == null) {

            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }

        return quantity1.add(
                quantity2,
                targetUnit
        );
    }

        // UC12 UPDATE
    // Generic subtraction demonstration

    public static <U extends IMeasurable>
    Quantity<U> demonstrateSubtraction(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        if (
                quantity1 == null
                || quantity2 == null
        ) {

            throw new IllegalArgumentException(
                    "Quantities cannot be null"
            );
        }

        return quantity1.subtract(
                quantity2
        );
    }

    // UC12 UPDATE
    // Generic subtraction with target unit

    public static <U extends IMeasurable>
    Quantity<U> demonstrateSubtraction(
            Quantity<U> quantity1,
            Quantity<U> quantity2,
            U targetUnit
    ) {

        if (
                quantity1 == null
                || quantity2 == null
        ) {

            throw new IllegalArgumentException(
                    "Quantities cannot be null"
            );
        }

        if (targetUnit == null) {

            throw new IllegalArgumentException(
                    "Target unit cannot be null"
            );
        }

        return quantity1.subtract(
                quantity2,
                targetUnit
        );
    }

    // UC12 UPDATE
    // Generic division demonstration

    public static <U extends IMeasurable>
    double demonstrateDivision(
            Quantity<U> quantity1,
            Quantity<U> quantity2
    ) {

        if (
                quantity1 == null
                || quantity2 == null
        ) {

            throw new IllegalArgumentException(
                    "Quantities cannot be null"
            );
        }

        return quantity1.divide(
                quantity2
        );
    }

    public static void main(String[] args) {

    // UC16 UPDATE
// Use JDBC database repository

//QuantityMeasurementRepository repository =
//        QuantityMeasurementDatabaseRepository.getInstance();
//
//    IQuantityMeasurementService service =
//            new QuantityMeasurementServiceImpl(repository);
//
//    // ---------------- LENGTH ----------------
//
//    QuantityDTO oneFoot =
//            new QuantityDTO(
//                    1,
//                    LengthUnit.FEET.name(),
//                    "LENGTH"
//            );
//
//    QuantityDTO twelveInches =
//            new QuantityDTO(
//                    12,
//                    LengthUnit.INCHES.name(),
//                    "LENGTH"
//            );
//
//    System.out.println(
//            "1 Foot == 12 Inches : "
//                    + service.compare(oneFoot, twelveInches)
//    );
//
//    QuantityDTO yard =
//            new QuantityDTO(
//                    0,
//                    LengthUnit.YARDS.name(),
//                    "LENGTH"
//            );
//
//    System.out.println(
//            "1 Foot -> Yard : "
//                    + service.convert(oneFoot, yard)
//    );
//
//    System.out.println(
//            "1 Foot + 12 Inches : "
//                    + service.add(oneFoot, twelveInches)
//    );
//
//    System.out.println(
//            "1 Foot - 12 Inches : "
//                    + service.subtract(oneFoot, twelveInches)
//    );
//
//    System.out.println(
//            "1 Foot / 12 Inches : "
//                    + service.divide(oneFoot, twelveInches)
//    );
//
//
//
//    // ---------------- WEIGHT ----------------
//
//    QuantityDTO oneKg =
//            new QuantityDTO(
//                    1,
//                    WeightUnit.KILOGRAM.name(),
//                    "WEIGHT"
//            );
//
//    QuantityDTO thousandGram =
//            new QuantityDTO(
//                    1000,
//                    WeightUnit.GRAM.name(),
//                    "WEIGHT"
//            );
//
//    System.out.println(
//            "1 Kg == 1000 Gram : "
//                    + service.compare(oneKg, thousandGram)
//    );
//
//
//
//    // ---------------- VOLUME ----------------
//
//    QuantityDTO oneGallon =
//            new QuantityDTO(
//                    1,
//                    VolumeUnit.GALLON.name(),
//                    "VOLUME"
//            );
//
//    QuantityDTO litres =
//            new QuantityDTO(
//                    3.78,
//                    VolumeUnit.LITRE.name(),
//                    "VOLUME"
//            );
//
//    System.out.println(
//            "1 Gallon == 3.78 Litres : "
//                    + service.compare(oneGallon, litres)
//    );
//
//
//
//    // ---------------- TEMPERATURE ----------------
//
//    QuantityDTO hundredCelsius =
//            new QuantityDTO(
//                    100,
//                    TemperatureUnit.CELSIUS.name(),
//                    "TEMPERATURE"
//            );
//
//    QuantityDTO fahrenheit =
//            new QuantityDTO(
//                    0,
//                    TemperatureUnit.FAHRENHEIT.name(),
//                    "TEMPERATURE"
//            );
//
//    System.out.println(
//            "100 C -> Fahrenheit : "
//                    + service.convert(
//                    hundredCelsius,
//                    fahrenheit
//            )
//    );
//
//
//
//    // ---------------- TEMPERATURE ARITHMETIC ----------------
//
//    try {
//
//        service.add(
//                hundredCelsius,
//                hundredCelsius
//        );
//
//    } catch (Exception e) {
//
//        System.out.println(
//                e.getMessage()
//        );
//    }
//
//    try {
//
//        service.subtract(
//                hundredCelsius,
//                hundredCelsius
//        );
//
//    } catch (Exception e) {
//
//        System.out.println(
//                e.getMessage()
//        );
//    }
//
//    try {
//
//        service.divide(
//                hundredCelsius,
//                hundredCelsius
//        );
//
//    } catch (Exception e) {
//
//        System.out.println(
//                e.getMessage()
//        );
//    }
//
//    ConnectionPool.closePool();
//
}
        
    }
