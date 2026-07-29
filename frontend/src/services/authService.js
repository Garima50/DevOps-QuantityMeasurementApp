const TOKEN_STORAGE_KEY = "qm_jwt";

// OAuth2SuccessHandler on the backend redirects here with the JWT as a
// query param (?token=...) once Google sign-in completes, so this is a
// normal full-page redirect — no popups or manual copy/paste needed.
export const GOOGLE_LOGIN_URL = "http://localhost:8080/oauth2/authorization/google";

export function redirectToGoogleLogin() {
    window.location.href = GOOGLE_LOGIN_URL;
}

function base64UrlDecode(segment) {
    const normalized = segment.replace(/-/g, "+").replace(/_/g, "/");
    const padded = normalized.padEnd(normalized.length + ((4 - (normalized.length % 4)) % 4), "=");
    return atob(padded);
}

// There's no "who am I" endpoint on the backend, and the JWT is only
// signed (not encrypted) — decoding the payload locally is the only way
// to read the logged-in user's email without another round trip.
export function decodeToken(token) {
    const parts = token.split(".");
    if (parts.length !== 3) {
        throw new Error("That doesn't look like a valid token.");
    }
    return JSON.parse(base64UrlDecode(parts[1])); // { sub: email, iat, exp }
}

export function isTokenExpired(payload) {
    if (!payload?.exp) return true;
    return payload.exp * 1000 < Date.now();
}

export function getStoredToken() {
    return localStorage.getItem(TOKEN_STORAGE_KEY);
}

export function storeToken(token) {
    localStorage.setItem(TOKEN_STORAGE_KEY, token);
}

export function clearToken() {
    localStorage.removeItem(TOKEN_STORAGE_KEY);
}
