import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import BrandMark from "./BrandMark";

function Header() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    function handleLogout() {
        // Purely local — the backend is stateless (JWT, no session), so
        // there's nothing to invalidate server-side. Clearing the stored
        // token is all "logout" means here.
        logout();
        navigate("/login", { replace: true });
    }

    return (
        <header className="header">
            <div className="header-brand">
                <BrandMark />
                <h1>Welcome To Quantity Measurement</h1>
            </div>

            {user && (
                <div className="user-badge">
                    <span className="user-avatar user-avatar-fallback" aria-hidden="true">
                        {user.email.charAt(0).toUpperCase()}
                    </span>
                    <span className="user-name">{user.email}</span>
                    <button type="button" className="logout-button" onClick={handleLogout}>
                        Logout
                    </button>
                </div>
            )}
        </header>
    );
}

export default Header;
