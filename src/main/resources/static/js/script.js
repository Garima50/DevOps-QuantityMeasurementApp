
const compareButton = document.getElementById("compareButton");
compareButton.addEventListener("click", compareQuantity);

const convertButton = document.getElementById("convertButton");
convertButton.addEventListener("click", convertQuantity);

const calculateButton =
    document.getElementById("calculateButton");

calculateButton.addEventListener("click", calculateQuantity);

async function compareQuantity() {

    const value1 = parseFloat(document.getElementById("compareValue1").value);
    const value2 = parseFloat(document.getElementById("compareValue2").value);

    const unit1 = document.getElementById("compareUnit1").value;
    const unit2 = document.getElementById("compareUnit2").value;

    if (isNaN(value1) || isNaN(value2)) {

        alert("Please enter both values.");

        return;
    }

    const requestBody = {

        firstQuantity: {

            value: value1,
            unit: unit1,
            measurementType: selectedMeasurementType

        },

        secondQuantity: {

            value: value2,
            unit: unit2,
            measurementType: selectedMeasurementType

        }

    };

    try {

        const response = await fetch("/api/quantity/compare", {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(requestBody)

        });

        const result = await response.json();

        document.getElementById("compareResult").innerHTML =
            result ? "✔ Equal" : "✖ Not Equal";

    }
    catch (error) {

        console.error(error);

        alert("Comparison failed.");

    }

}

async function convertQuantity() {

    const value = parseFloat(document.getElementById("convertValue").value);

    if (isNaN(value)) {
        alert("Please enter a value.");
        return;
    }

    const requestBody = {

        sourceQuantity: {

            value: value,
            unit: document.getElementById("convertFromUnit").value,
            measurementType: selectedMeasurementType

        },

        targetQuantity: {

            value: 0,
            unit: document.getElementById("convertToUnit").value,
            measurementType: selectedMeasurementType

        }

    };

    try {

        const response = await fetch("/api/quantity/convert", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(requestBody)

        });

        if (!response.ok)
            throw new Error("HTTP " + response.status);

        const result = await response.json();

        document.getElementById("convertResult").innerHTML =
            result.value + " " + result.unit;

    }
    catch (error) {

        console.error(error);

        alert("Conversion failed.");

    }

}


async function calculateQuantity() {

    const operator =
        document.getElementById("operator").value;

    let endpoint = "";

    switch (operator) {

        case "add":
            endpoint = "/api/quantity/add";
            break;

        case "subtract":
            endpoint = "/api/quantity/subtract";
            break;

        case "divide":
            endpoint = "/api/quantity/divide";
            break;

    }

    const requestBody = {

        firstQuantity: {

            value: parseFloat(document.getElementById("firstValue").value),

            unit: document.getElementById("firstUnit").value,

            measurementType: selectedMeasurementType

        },

        secondQuantity: {

            value: parseFloat(document.getElementById("secondValue").value),

            unit: document.getElementById("secondUnit").value,

            measurementType: selectedMeasurementType

        },

        targetQuantity: {

            value: 0,

            unit: document.getElementById("resultUnit").value,

            measurementType: selectedMeasurementType

        }

    };

    try {

        const response = await fetch(endpoint, {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(requestBody)

        });

        if (!response.ok)
            throw new Error("HTTP " + response.status);

        let result = await response.json();

        if (typeof result === "number") {

            document.getElementById("arithmeticResult").innerHTML =
                result;

        }
        else {

            document.getElementById("arithmeticResult").innerHTML =
                result.value + " " + result.unit;

        }

    }
    catch (error) {

        console.error(error);

        alert("Arithmetic failed.");

    }

}

const units = {

    LENGTH: [
        "FEET",
        "INCHES",
        "YARDS",
        "CENTIMETERS"
    ],

    WEIGHT: [
        "KILOGRAM",
        "GRAM",
        "POUND"
    ],

    TEMPERATURE: [
        "CELSIUS",
        "FAHRENHEIT",
        "KELVIN"
    ],

    VOLUME: [
        "LITRE",
        "MILLILITRE",
        "GALLON"
    ]

};

const unitDropdownIds = [
    "compareUnit1",
    "compareUnit2",
    "convertFromUnit",
    "convertToUnit",
    "firstUnit",
    "secondUnit",
    "resultUnit"
];

const comparisonBtn = document.getElementById("compareBtn");
const conversionBtn = document.getElementById("convertBtn");
const arithmeticBtn = document.getElementById("arithmeticBtn");

const comparisonSection = document.getElementById("comparisonSection");
const conversionSection = document.getElementById("conversionSection");
const arithmeticSection = document.getElementById("arithmeticSection");

function hideAllSections() {

    comparisonSection.style.display = "none";
    conversionSection.style.display = "none";
    arithmeticSection.style.display = "none";

    comparisonBtn.classList.remove("active");
    conversionBtn.classList.remove("active");
    arithmeticBtn.classList.remove("active");

}

comparisonBtn.addEventListener("click", () => {

    hideAllSections();

    comparisonSection.style.display = "block";

    comparisonBtn.classList.add("active");

});


conversionBtn.addEventListener("click", () => {

    hideAllSections();

    conversionSection.style.display = "block";

    conversionBtn.classList.add("active");

});


arithmeticBtn.addEventListener("click", () => {

    hideAllSections();

    arithmeticSection.style.display = "block";

    arithmeticBtn.classList.add("active");

});

let selectedMeasurementType = "LENGTH";

const cards = document.querySelectorAll(".card");

cards.forEach(card => {

    card.addEventListener("click", () => {

        cards.forEach(c => c.classList.remove("active"));

        card.classList.add("active");

        selectedMeasurementType = card.dataset.type;

        if (selectedMeasurementType === "TEMPERATURE") {

            arithmeticBtn.disabled = true;
            arithmeticBtn.style.opacity = "0.5";
            arithmeticBtn.style.cursor = "not-allowed";

            if (arithmeticSection.style.display === "block") {
                comparisonBtn.click();
            }

        } else {

            arithmeticBtn.disabled = false;
            arithmeticBtn.style.opacity = "1";
            arithmeticBtn.style.cursor = "pointer";

        }

        populateUnits();

    });

});

const dropdowns = document.querySelectorAll("select");

function populateUnits() {

    const currentUnits = units[selectedMeasurementType];

    unitDropdownIds.forEach(id => {

        const select = document.getElementById(id);

        select.innerHTML = "";

        currentUnits.forEach(unit => {

            const option = document.createElement("option");

            option.value = unit;
            option.textContent = unit;

            select.appendChild(option);

        });

    });

}
populateUnits();