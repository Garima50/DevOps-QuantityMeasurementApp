import axios from "axios";
import { getStoredToken } from "./authService";

// Relative baseURL — Vite's dev proxy (vite.config.js) forwards this to
// http://localhost:8080 server-to-server, so the browser only ever talks
// to :5173 and CORS never comes into play.
const quantityApi = axios.create({
    baseURL: "/api/quantity",
    headers: {
        "Content-Type": "application/json",
    },
});

// /api/quantity/** is currently permitAll on the backend, so this isn't
// strictly required today — but it's what JwtAuthenticationFilter expects
// (Authorization: Bearer <token>), so requests are already correctly
// authenticated if/when you tighten that endpoint to require login later.
quantityApi.interceptors.request.use((config) => {
    const token = getStoredToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export async function compare(compareRequest) {
    const response = await quantityApi.post("/compare", compareRequest);
    return response.data; // boolean
}

export async function convert(convertRequest) {
    const response = await quantityApi.post("/convert", convertRequest);
    return response.data; // QuantityDTO: { value, unit, measurementType }
}

export async function add(arithmeticRequest) {
    const response = await quantityApi.post("/add", arithmeticRequest);
    return response.data; // QuantityDTO
}

export async function subtract(arithmeticRequest) {
    const response = await quantityApi.post("/subtract", arithmeticRequest);
    return response.data; // QuantityDTO
}

export async function divide(arithmeticRequest) {
    const response = await quantityApi.post("/divide", arithmeticRequest);
    return response.data; // raw number — divide has no unit, it's a ratio
}

export async function getHistory() {
    const response = await quantityApi.get("/history");
    return response.data; // QuantityMeasurementEntity[], most recent first
}
