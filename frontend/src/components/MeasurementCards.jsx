
import MeasurementCard from "./MeasurementCard";

import length from "../assets/images/length.png";
import weight from "../assets/images/weight.png";
import temperature from "../assets/images/temperature.png";
import volume from "../assets/images/volume.png";

const measurementTypes = [
    { title: "Length", icon: length },
    { title: "Weight", icon: weight },
    { title: "Temperature", icon: temperature },
    { title: "Volume", icon: volume },
];

function MeasurementCards({ selectedMeasurementType, setSelectedMeasurementType }) {
    return (
        <>
            <h5 className="section-title">CHOOSE TYPE</h5>

            <div className="measurement-container">
                {measurementTypes.map((type) => (
                    <MeasurementCard
                        key={type.title}
                        icon={type.icon}
                        title={type.title}
                        selected={selectedMeasurementType === type.title}
                        onClick={() => setSelectedMeasurementType(type.title)}
                    />
                ))}

            </div>

        </>
    );
}

export default MeasurementCards;
