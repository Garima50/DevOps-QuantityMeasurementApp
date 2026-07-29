import { useEffect, useState } from "react";
import { getUnitsForMeasurementType } from "../data/units";
import { add, subtract, divide } from "../services/quantityService";
import { extractErrorMessage } from "../utils/apiError";

const operators = ["+", "-", "÷"];

const operatorHandlers = {
    "+": add,
    "-": subtract,
    "÷": divide,
};

function ArithmeticSection({ selectedMeasurementType }) {
    // The backend explicitly rejects arithmetic on temperature
    // (validateArithmeticOperands throws for TemperatureUnit) — 20°C + 5°C
    // isn't a meaningful operation, so there's no point letting the user
    // submit a request that's guaranteed to fail.
    const isTemperature = selectedMeasurementType.toUpperCase() === "TEMPERATURE";

    const availableUnits = getUnitsForMeasurementType(selectedMeasurementType);
    const firstUnit = availableUnits[0];
    const secondUnit = availableUnits[1] ?? firstUnit;

    const [value1, setValue1] = useState(1);
    const [value2, setValue2] = useState(1);
    const [unit1, setUnit1] = useState(firstUnit);
    const [unit2, setUnit2] = useState(secondUnit);
    const [operator, setOperator] = useState("+");
    const [resultUnit, setResultUnit] = useState(firstUnit);
    const [resultMessage, setResultMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const isDivide = operator === "÷";

    useEffect(() => {
        setUnit1(firstUnit);
        setUnit2(secondUnit);
        setResultUnit(firstUnit);
        setResultMessage("");
        setErrorMessage("");
    }, [firstUnit, secondUnit]);

    function validate() {
        if (isTemperature) {
            return "Arithmetic operations aren't available for Temperature.";
        }
        if (value1 === "" || Number.isNaN(Number(value1))) {
            return "Please enter a valid first value.";
        }
        if (value2 === "" || Number.isNaN(Number(value2))) {
            return "Please enter a valid second value.";
        }
        if (isDivide && Number(value2) === 0) {
            return "Can't divide by zero — try a different value.";
        }
        return "";
    }

    async function handleCalculate() {
        const validationError = validate();
        if (validationError) {
            setErrorMessage(validationError);
            setResultMessage("");
            return;
        }

        setErrorMessage("");
        setIsLoading(true);

        const measurementType = selectedMeasurementType.toUpperCase();
        const arithmeticRequest = {
            firstQuantity: { value: Number(value1), unit: unit1, measurementType },
            secondQuantity: { value: Number(value2), unit: unit2, measurementType },
            // The /divide endpoint doesn't accept a target unit — division
            // produces a plain ratio, not a quantity in a particular unit.
            ...(isDivide ? {} : { targetQuantity: { value: 0, unit: resultUnit, measurementType } }),
        };

        try {
            const performOperation = operatorHandlers[operator];
            const result = await performOperation(arithmeticRequest);
            // add/subtract return a QuantityDTO ({ value, unit, ... }); divide returns a raw number.
            if (isDivide) {
                setResultMessage(`${result}`);
            } else {
                setResultMessage(`${result.value} ${result.unit}`);
            }
        } catch (error) {
            setResultMessage("");
            setErrorMessage(extractErrorMessage(error, "Unable to calculate right now. Please try again."));
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <section key={selectedMeasurementType} className="arithmetic-section" aria-labelledby="arithmetic-title">
            <h2 id="arithmetic-title" className="sr-only">Calculate quantities</h2>

            {isTemperature && (
                <p className="field-error">Arithmetic operations aren't available for Temperature.</p>
            )}

            <div className="arithmetic-values">
                <label className="quantity-field">
                    <span>VALUE 1</span>
                    <input
                        type="number"
                        value={value1}
                        onChange={(event) => setValue1(event.target.value)}
                        aria-label="First value"
                        disabled={isTemperature}
                    />
                </label>

                <label className="quantity-field">
                    <span>VALUE 2</span>
                    <input
                        type="number"
                        value={value2}
                        onChange={(event) => setValue2(event.target.value)}
                        aria-label="Second value"
                        disabled={isTemperature}
                    />
                </label>
            </div>

            <div className="arithmetic-controls">
                <select
                    className="arithmetic-unit"
                    value={unit1}
                    onChange={(event) => setUnit1(event.target.value)}
                    aria-label="First unit"
                    disabled={isTemperature}
                >
                    {availableUnits.map((unit) => (
                        <option key={unit} value={unit}>{unit}</option>
                    ))}
                </select>

                <select
                    className="arithmetic-operator"
                    value={operator}
                    onChange={(event) => setOperator(event.target.value)}
                    aria-label="Arithmetic operator"
                    disabled={isTemperature}
                >
                    {operators.map((op) => (
                        <option key={op} value={op}>{op}</option>
                    ))}
                </select>

                <select
                    className="arithmetic-unit"
                    value={unit2}
                    onChange={(event) => setUnit2(event.target.value)}
                    aria-label="Second unit"
                    disabled={isTemperature}
                >
                    {availableUnits.map((unit) => (
                        <option key={unit} value={unit}>{unit}</option>
                    ))}
                </select>
            </div>

            {errorMessage && !isTemperature && <p className="field-error">{errorMessage}</p>}

            <button
                className="compare-button"
                type="button"
                onClick={handleCalculate}
                disabled={isLoading || isTemperature}
            >
                {isLoading && <span className="spinner-inline" aria-hidden="true" />}
                {isLoading ? "Calculating..." : "Calculate"}
            </button>

            <div className="compare-result arithmetic-result" aria-live="polite">
                <div>
                    <span>RESULT</span>
                    <p>{resultMessage || "Calculated value will appear here."}</p>
                </div>

                <select
                    className="arithmetic-result-unit"
                    value={resultUnit}
                    onChange={(event) => setResultUnit(event.target.value)}
                    aria-label="Result unit"
                    disabled={isTemperature || isDivide}
                    title={isDivide ? "Division produces a ratio, not a unit" : undefined}
                >
                    {availableUnits.map((unit) => (
                        <option key={unit} value={unit}>{unit}</option>
                    ))}
                </select>
            </div>
        </section>
    );
}

export default ArithmeticSection;
