function MeasurementCard({ icon, title, selected, onClick }) {
    return (
        <div
            className={`measurement-card ${selected ? "active" : ""}`}
            onClick={onClick}
        >
            <img src={icon} alt={title} />
            <p>{title}</p>
        </div>
    );
}

export default MeasurementCard;