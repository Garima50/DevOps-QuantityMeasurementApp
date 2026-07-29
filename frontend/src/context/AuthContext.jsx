import { createContext, useCallback, useContext, useEffect, useState } from "react";
import {
    clearToken,
    decodeToken,
    getStoredToken,
    isTokenExpired,
    redirectToGoogleLogin,
    storeToken,
} from "../services/authService";

const AuthContext = createContext(null);

function buildUserFromToken(token) {
    const payload = decodeToken(token);
    if (isTokenExpired(payload)) {
        throw new Error("Your session has expired. Please sign in again.");
    }
    return { email: payload.sub };
}

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const existingToken = getStoredToken();
        if (existingToken) {
            try {
                setUser(buildUserFromToken(existingToken));
            } catch {
                clearToken();
                setUser(null);
            }
        }
        setIsLoading(false);
    }, []);

    // Kicks off Google sign-in — a normal full-page redirect. The backend
    // redirects back to /login?token=... when it's done (see LoginPage.jsx).
    const login = useCallback(() => {
        redirectToGoogleLogin();
    }, []);

    // Called with the token once it arrives back in the URL after Google
    // sign-in. Throws if the token is malformed or expired — LoginPage
    // shows the error.
    const completeLogin = useCallback((token) => {
        const trimmed = token.trim();
        const nextUser = buildUserFromToken(trimmed);
        storeToken(trimmed);
        setUser(nextUser);
        return nextUser;
    }, []);

    const logout = useCallback(() => {
        clearToken();
        setUser(null);
    }, []);

    const value = {
        user,
        isAuthenticated: Boolean(user),
        isLoading,
        login,
        completeLogin,
        logout,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
}
