export const units = Object.freeze({
    LENGTH: Object.freeze(["FEET", "INCHES", "YARDS", "CENTIMETERS"]),
    WEIGHT: Object.freeze(["KILOGRAM", "GRAM", "POUND"]),
    TEMPERATURE: Object.freeze(["CELSIUS", "FAHRENHEIT", "KELVIN"]),
    VOLUME: Object.freeze(["LITRE", "MILLILITRE", "GALLON"]),
});

export function getUnitsForMeasurementType(measurementType) {
    return units[measurementType.toUpperCase()] ?? [];
}
