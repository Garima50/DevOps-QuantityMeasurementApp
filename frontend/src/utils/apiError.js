// GlobalExceptionHandler on the backend returns { message: "..." } for both
// validation errors (400) and unexpected errors (500). Prefer that message
// when present, since it's usually more specific than a generic fallback
// (e.g. "Cannot divide by zero.", "Arithmetic operations are not supported
// for temperature.").
export function extractErrorMessage(error, fallback) {
    return error?.response?.data?.message || fallback;
}
