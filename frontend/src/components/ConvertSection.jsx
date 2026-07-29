import { useEffect, useState } from "react";
import { getUnitsForMeasurementType } from "../data/units";
import { convert } from "../services/quantityService";
import { extractErrorMessage } from "../utils/apiError";

function ConvertSection({ selectedMeasurementType }) {
    const availableUnits = getUnitsForMeasurementType(selectedMeasurementType);
    const sourceUnit = availableUnits[0];
    const targetUnit = availableUnits[1] ?? sourceUnit;

    const [value, setValue] = useState(1);
    const [fromUnit, setFromUnit] = useState(sourceUnit);
    const [toUnit, setToUnit] = useState(targetUnit);
    const [resultMessage, setResultMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setFromUnit(sourceUnit);
        setToUnit(targetUnit);
        setResultMessage("");
        setErrorMessage("");
    }, [sourceUnit, targetUnit]);

    function validate() {
        if (value === "" || Number.isNaN(Number(value))) {
            return "Please enter a valid number to convert.";
        }
        return "";
    }

    async function handleConvert() {
        const validationError = validate();
        if (validationError) {
            setErrorMessage(validationError);
            setResultMessage("");
            return;
        }

        setErrorMessage("");
        setIsLoading(true);

        const measurementType = selectedMeasurementType.toUpperCase();
        // Backend's ConvertRequest wants full QuantityDTOs for both sides.
        // Only targetQuantity's unit/measurementType are actually used by
        // the service (its value is ignored), but the field still needs
        // to be present since QuantityDTO.value is a primitive double.
        const convertRequest = {
            sourceQuantity: {
                value: Number(value),
                unit: fromUnit,
                measurementType,
            },
            targetQuantity: {
                value: 0,
                unit: toUnit,
                measurementType,
            },
        };

        try {
            const result = await convert(convertRequest); // { value, unit, measurementType }
            setResultMessage(`${result.value} ${result.unit}`);
        } catch (error) {
            setResultMessage("");
            setErrorMessage(extractErrorMessage(error, "Unable to convert right now. Please try again."));
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <section key={selectedMeasurementType} className="compare-section" aria-labelledby="convert-title">
            <h2 id="convert-title" className="sr-only">Convert quantity</h2>

            <div className="compare-fields">
                <label className="quantity-field">
                    <span>FROM</span>
                    <input
                        type="number"
                        value={value}
                        onChange={(event) => setValue(event.target.value)}
                        aria-label="Value to convert"
                    />
                    <select
                        value={fromUnit}
                        onChange={(event) => setFromUnit(event.target.value)}
                        aria-label="Source unit"
                    >
                        {availableUnits.map((unit) => (
                            <option key={unit} value={unit}>{unit}</option>
                        ))}
                    </select>
                </label>

                <label className="quantity-field convert-target-field">
                    <span>TO</span>
                    <select
                        value={toUnit}
                        onChange={(event) => setToUnit(event.target.value)}
                        aria-label="Target unit"
                    >
                        {availableUnits.map((unit) => (
                            <option key={unit} value={unit}>{unit}</option>
                        ))}
                    </select>
                </label>
            </div>

            {errorMessage && <p className="field-error">{errorMessage}</p>}

            <button className="compare-button" type="button" onClick={handleConvert} disabled={isLoading}>
                {isLoading && <span className="spinner-inline" aria-hidden="true" />}
                {isLoading ? "Converting..." : "Convert"}
            </button>

            <div className="compare-result" aria-live="polite">
                <span>CONVERTED RESULT</span>
                <p>{resultMessage || "Converted value will appear here."}</p>
            </div>
        </section>
    );
}

export default ConvertSection;
