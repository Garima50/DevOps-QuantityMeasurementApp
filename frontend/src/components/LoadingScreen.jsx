function LoadingScreen({ message = "Loading..." }) {
    return (
        <div className="route-loading">
            <span className="spinner" aria-hidden="true" />
            <p>{message}</p>
        </div>
    );
}

export default LoadingScreen;
