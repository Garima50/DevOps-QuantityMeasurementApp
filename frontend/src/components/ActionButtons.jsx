const actions = [
    { label: "Comparison", value: "comparison" },
    { label: "Conversion", value: "conversion" },
    { label: "Arithmetic", value: "arithmetic" },
    { label: "History", value: "history" },
];

function ActionButtons({ selectedAction, setSelectedAction }) {
    return (
        <section className="action-section" aria-label="Choose an action">
            <h5 className="section-title">CHOOSE ACTION</h5>

            <div className="action-buttons">
                {actions.map((action) => (
                    <button
                        key={action.value}
                        type="button"
                        className={`action-button ${selectedAction === action.value ? "active" : ""}`}
                        onClick={() => setSelectedAction(action.value)}
                    >
                        {action.label}
                    </button>
                ))}
            </div>
        </section>
    );
}

export default ActionButtons;
