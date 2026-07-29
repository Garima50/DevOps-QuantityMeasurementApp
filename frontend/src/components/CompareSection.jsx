import { useEffect, useState } from "react";
import { getUnitsForMeasurementType } from "../data/units";
import { compare } from "../services/quantityService";
import { extractErrorMessage } from "../utils/apiError";

function CompareSection({ selectedMeasurementType }) {
    const availableUnits = getUnitsForMeasurementType(selectedMeasurementType);
    const sourceUnit = availableUnits[0];
    const targetUnit = availableUnits[1] ?? sourceUnit;
    const [firstValue, setFirstValue] = useState(1);
    const [secondValue, setSecondValue] = useState(1000);
    const [firstUnit, setFirstUnit] = useState(sourceUnit);
    const [secondUnit, setSecondUnit] = useState(targetUnit);
    const [resultMessage, setResultMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setFirstUnit(sourceUnit);
        setSecondUnit(targetUnit);
        setResultMessage("");
        setErrorMessage("");
    }, [sourceUnit, targetUnit]);

    function validate() {
        if (firstValue === "" || Number.isNaN(Number(firstValue))) {
            return "Please enter a valid first value.";
        }
        if (secondValue === "" || Number.isNaN(Number(secondValue))) {
            return "Please enter a valid second value.";
        }
        return "";
    }

    async function handleCompare() {
        const validationError = validate();
        if (validationError) {
            setErrorMessage(validationError);
            setResultMessage("");
            return;
        }

        setErrorMessage("");
        setIsLoading(true);

        const measurementType = selectedMeasurementType.toUpperCase();
        const compareRequest = {
            firstQuantity: {
                value: Number(firstValue),
                unit: firstUnit,
                measurementType,
            },
            secondQuantity: {
                value: Number(secondValue),
                unit: secondUnit,
                measurementType,
            },
        };

        try {
            const isEqual = await compare(compareRequest);
            setResultMessage(isEqual ? "✔ Equal" : "✖ Not Equal");
        } catch (error) {
            setResultMessage("");
            setErrorMessage(extractErrorMessage(error, "Unable to compare quantities. Please try again."));
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <section className="compare-section" aria-labelledby="compare-title">
            <h2 id="compare-title" className="sr-only">Compare quantities</h2>

            <div className="compare-fields">
                <label className="quantity-field">
                    <span>FROM</span>
                    <input
                        type="number"
                        value={firstValue}
                        onChange={(event) => setFirstValue(event.target.value)}
                        aria-label="From value"
                    />
                    <select
                        value={firstUnit}
                        onChange={(event) => setFirstUnit(event.target.value)}
                        aria-label="From unit"
                    >
                        {availableUnits.map((unit) => (
                            <option key={unit} value={unit}>{unit}</option>
                        ))}
                    </select>
                </label>

                <label className="quantity-field">
                    <span>TO</span>
                    <input
                        type="number"
                        value={secondValue}
                        onChange={(event) => setSecondValue(event.target.value)}
                        aria-label="To value"
                    />
                    <select
                        value={secondUnit}
                        onChange={(event) => setSecondUnit(event.target.value)}
                        aria-label="To unit"
                    >
                        {availableUnits.map((unit) => (
                            <option key={unit} value={unit}>{unit}</option>
                        ))}
                    </select>
                </label>
            </div>

            {errorMessage && <p className="field-error">{errorMessage}</p>}

            <button className="compare-button" type="button" onClick={handleCompare} disabled={isLoading}>
                {isLoading && <span className="spinner-inline" aria-hidden="true" />}
                {isLoading ? "Comparing..." : "Compare"}
            </button>

            <div className="compare-result" aria-live="polite">
                <span>RESULT</span>
                <p>{resultMessage || "Comparison result will appear here."}</p>
            </div>
        </section>
    );
}

export default CompareSection;
