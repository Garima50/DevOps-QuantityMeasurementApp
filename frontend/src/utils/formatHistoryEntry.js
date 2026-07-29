// QuantityMeasurementEntity stores every operation in one flat shape, but
// which fields are meaningful depends on the operation:
// - COMPARE: numericResult is 1.0 (equal) or 0.0 (not equal)
// - DIVIDE: numericResult is the raw ratio — divide has no unit
// - ADD/SUBTRACT: resultValue + resultUnit hold the answer
// - CONVERT: resultValue + resultUnit hold the answer; secondValue is just
//   a placeholder the frontend sends (0) to specify the target unit, not a
//   real second operand, so it's left out of the expression.
export function formatHistoryEntry(entry) {
    const {
        operation,
        firstValue,
        firstUnit,
        secondValue,
        secondUnit,
        resultValue,
        resultUnit,
        numericResult,
        errorMessage,
    } = entry;

    if (errorMessage) {
        return {
            label: operation,
            expression: `${firstValue} ${firstUnit}`,
            result: errorMessage,
            isError: true,
        };
    }

    switch (operation) {
        case "COMPARE":
            return {
                label: "Compare",
                expression: `${firstValue} ${firstUnit}   vs   ${secondValue} ${secondUnit}`,
                result: numericResult === 1 ? "Equal" : "Not equal",
            };
        case "DIVIDE":
            return {
                label: "Divide",
                expression: `${firstValue} ${firstUnit}   ÷   ${secondValue} ${secondUnit}`,
                result: `${numericResult}`,
            };
        case "ADD":
            return {
                label: "Add",
                expression: `${firstValue} ${firstUnit}   +   ${secondValue} ${secondUnit}`,
                result: `${resultValue} ${resultUnit}`,
            };
        case "SUBTRACT":
            return {
                label: "Subtract",
                expression: `${firstValue} ${firstUnit}   −   ${secondValue} ${secondUnit}`,
                result: `${resultValue} ${resultUnit}`,
            };
        case "CONVERT":
            return {
                label: "Convert",
                expression: `${firstValue} ${firstUnit}   →   ${resultUnit}`,
                result: `${resultValue} ${resultUnit}`,
            };
        default:
            return {
                label: operation ?? "Operation",
                expression: `${firstValue} ${firstUnit}`,
                result: resultValue != null ? `${resultValue} ${resultUnit}` : `${numericResult}`,
            };
    }
}
