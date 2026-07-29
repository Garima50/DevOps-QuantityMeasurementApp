import { useEffect, useState } from "react";
import "./LoginPage.css";
import { FcGoogle } from "react-icons/fc";
import { Navigate, useNavigate, useSearchParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const SPEC_TAGS = ["CM ⇄ IN", "KG ⇄ LB", "°C ⇄ °F", "L ⇄ GAL", "A ⇄ B COMPARE", "+ − × ÷"];

function CaliperIllustration() {
  const ticks = Array.from({ length: 29 }, (_, i) => i);

  return (
    <svg viewBox="0 0 360 196" width="100%" height="100%" role="img" aria-hidden="true">
      {/* beam */}
      <line x1="20" y1="46" x2="300" y2="46" stroke="#FFB627" strokeWidth="4" strokeLinecap="round" />

      {/* scale ticks along the beam */}
      {ticks.map((i) => {
        const x = 20 + i * 10;
        const major = i % 5 === 0;
        return (
          <line
            key={i}
            x1={x}
            y1={major ? 38 : 41}
            x2={x}
            y2="46"
            stroke="#FFB627"
            strokeWidth={major ? 1.6 : 1}
            opacity={major ? 0.9 : 0.45}
          />
        );
      })}
      <text x="16" y="34" fontFamily="'JetBrains Mono', monospace" fontSize="9" fill="#FFB627" opacity="0.6">0</text>
      <text x="292" y="34" fontFamily="'JetBrains Mono', monospace" fontSize="9" fill="#FFB627" opacity="0.6">28</text>

      {/* jaws */}
      <line x1="95" y1="46" x2="95" y2="150" stroke="#FFB627" strokeWidth="4" strokeLinecap="round" />
      <line x1="205" y1="46" x2="205" y2="150" stroke="#FFB627" strokeWidth="4" strokeLinecap="round" />
      <circle cx="95" cy="46" r="3" fill="#FFB627" />
      <circle cx="205" cy="46" r="3" fill="#FFB627" />

      {/* slider carriage */}
      <rect x="195" y="36" width="20" height="20" rx="3" fill="rgba(255,255,255,0.07)" stroke="#FFB627" strokeWidth="2" />

      {/* measured object */}
      <rect x="95" y="98" width="110" height="52" rx="8" fill="rgba(255,255,255,0.07)" stroke="#FFB627" strokeWidth="2" />

      {/* dimension line */}
      <line x1="95" y1="150" x2="95" y2="172" stroke="#FFB627" strokeWidth="1" opacity="0.6" />
      <line x1="205" y1="150" x2="205" y2="172" stroke="#FFB627" strokeWidth="1" opacity="0.6" />
      <line x1="95" y1="164" x2="205" y2="164" stroke="#FFB627" strokeWidth="1.5" />
      <polyline points="99,161 95,164 99,167" fill="none" stroke="#FFB627" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
      <polyline points="201,161 205,164 201,167" fill="none" stroke="#FFB627" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
      <text x="150" y="190" textAnchor="middle" fontFamily="'JetBrains Mono', monospace" fontSize="13" fill="#FFB627">
        42.0 mm
      </text>
    </svg>
  );
}

function LoginPage() {
  const { isAuthenticated, isLoading, login, completeLogin } = useAuth();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const [loginError, setLoginError] = useState("");
  const [isProcessingToken, setIsProcessingToken] = useState(false);

  // The backend redirects here with ?token=... right after Google sign-in
  // completes — pick it up, log in, and drop straight into the dashboard.
  useEffect(() => {
    const tokenFromUrl = searchParams.get("token");
    if (!tokenFromUrl) return;

    setIsProcessingToken(true);
    try {
      completeLogin(tokenFromUrl);
      navigate("/home", { replace: true });
    } catch (error) {
      setLoginError(error.message || "Sign-in didn't go through. Please try again.");
      setIsProcessingToken(false);
      navigate("/login", { replace: true }); // strip the bad token out of the URL
    }
    // Only run this once, right after the redirect lands.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // Already have a valid session — skip straight to the dashboard.
  if (!isLoading && isAuthenticated) {
    return <Navigate to="/home" replace />;
  }

  function handleContinueWithGoogle() {
    login();
  }

  return (
    <div className="login-page">
      <div className="login-left">
        <div className="grid-overlay" aria-hidden="true" />

        <div className="brand">
          <div className="logo" aria-hidden="true">
            <svg viewBox="0 0 48 48" width="26" height="26" fill="none">
              <path d="M6 12 L42 12" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
              <path d="M12 12 L12 24" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
              <path d="M36 12 L36 24" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
              <path d="M12 24 L16 34" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
              <path d="M36 24 L32 34" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />
              <path d="M16 34 L32 34" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeDasharray="2 3" />
            </svg>
          </div>

          <p className="eyebrow">Spring Boot · React · OAuth 2.0</p>
          <h1>Quantity Measurement</h1>
          <p className="lede">
            Compare, convert, and calculate physical quantities — backed by a
            Spring Boot API and secured with Google sign-in.
          </p>

          <ul className="spec-tags">
            {SPEC_TAGS.map((tag) => (
              <li key={tag}>{tag}</li>
            ))}
          </ul>
        </div>

        <div className="caliper-illustration">
          <CaliperIllustration />
        </div>
      </div>

      <div className="login-right">
        <div className="login-card">
          <div className="card-ruler" aria-hidden="true" />
          <div className="card-body">
            <h2>Welcome back</h2>
            <p>Sign in to your measurement workspace.</p>

            {loginError && (
              <p className="login-error" role="alert">{loginError}</p>
            )}

            <button
              className="google-btn"
              onClick={handleContinueWithGoogle}
              type="button"
              disabled={isProcessingToken}
            >
              <FcGoogle size={20} />
              {isProcessingToken && <span className="spinner-inline" aria-hidden="true" />}
              {isProcessingToken ? "Signing you in..." : "Continue with Google"}
            </button>

            <span className="oauth-text">
              Secured with Google OAuth 2.0 — no passwords stored.
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;