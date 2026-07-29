function BrandMark({ size = 20 }) {
    return (
        <div className="brand-mark" aria-hidden="true">
            <svg viewBox="0 0 48 48" width={size} height={size} fill="none">
                <path d="M6 12 L42 12" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
                <path d="M12 12 L12 24" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
                <path d="M36 12 L36 24" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
                <path d="M12 24 L16 34" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
                <path d="M36 24 L32 34" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
                <path d="M16 34 L32 34" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeDasharray="2 3" />
            </svg>
        </div>
    );
}

export default BrandMark;
