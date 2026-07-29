import { useEffect, useState } from "react";
import { getHistory } from "../services/quantityService";
import { extractErrorMessage } from "../utils/apiError";
import { formatHistoryEntry } from "../utils/formatHistoryEntry";

function HistorySection() {
    const [entries, setEntries] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState("");

    async function loadHistory() {
        setIsLoading(true);
        setErrorMessage("");
        try {
            const data = await getHistory();
            setEntries(data);
        } catch (error) {
            setErrorMessage(extractErrorMessage(error, "Unable to load history right now."));
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        loadHistory();
    }, []);

    return (
        <section className="history-section" aria-labelledby="history-title">
            <div className="history-header">
                <h2 id="history-title" className="sr-only">Recent activity</h2>
                <span className="history-count">
                    {isLoading ? "Loading…" : `${entries.length} ${entries.length === 1 ? "entry" : "entries"}`}
                </span>
                <button type="button" className="history-refresh" onClick={loadHistory} disabled={isLoading}>
                    {isLoading && <span className="spinner-inline-dark" aria-hidden="true" />}
                    {isLoading ? "Refreshing…" : "Refresh"}
                </button>
            </div>

            {errorMessage && <p className="field-error">{errorMessage}</p>}

            {!isLoading && !errorMessage && entries.length === 0 && (
                <p className="history-empty">
                    No history yet — run a comparison, conversion, or calculation to see it here.
                </p>
            )}

            {entries.length > 0 && (
                <ul className="history-list">
                    {entries.map((entry) => {
                        const { label, expression, result, isError } = formatHistoryEntry(entry);
                        return (
                            <li key={entry.id} className="history-entry">
                                <div className="history-entry-top">
                                    <span className="history-badge">{label}</span>
                                    <span className="history-id">#{entry.id}</span>
                                </div>
                                <p className="history-expression">{expression}</p>
                                <p className={`history-result ${isError ? "history-result-error" : ""}`}>
                                    {result}
                                </p>
                            </li>
                        );
                    })}
                </ul>
            )}
        </section>
    );
}

export default HistorySection;
