import { useState } from "react";
import Header from "../components/Header";
import MeasurementCards from "../components/MeasurementCards";
import ActionButtons from "../components/ActionButtons";
import CompareSection from "../components/CompareSection";
import ConvertSection from "../components/ConvertSection";
import ArithmeticSection from "../components/ArithmeticSection";
import HistorySection from "../components/HistorySection";

function HomePage() {
    const [selectedMeasurementType, setSelectedMeasurementType] = useState("Length");
    const [selectedAction, setSelectedAction] = useState("comparison");

    return (
        <div className="app">
            <div className="app-ruler" aria-hidden="true" />

            <Header />

            <main className="app-content">
                {selectedAction !== "history" && (
                    <MeasurementCards
                        selectedMeasurementType={selectedMeasurementType}
                        setSelectedMeasurementType={setSelectedMeasurementType}
                    />
                )}
                <ActionButtons
                    selectedAction={selectedAction}
                    setSelectedAction={setSelectedAction}
                />

                {selectedAction === "comparison" && (
                    <CompareSection selectedMeasurementType={selectedMeasurementType} />
                )}
                {selectedAction === "conversion" && (
                    <ConvertSection selectedMeasurementType={selectedMeasurementType} />
                )}
                {selectedAction === "arithmetic" && (
                    <ArithmeticSection selectedMeasurementType={selectedMeasurementType} />
                )}
                {selectedAction === "history" && <HistorySection />}
            </main>

        </div>
    );
}

export default HomePage;
