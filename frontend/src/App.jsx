import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import ProtectedRoute from "./components/ProtectedRoute";
import { AuthProvider } from "./context/AuthContext";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Navigate to="/login" replace />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route
                        path="/home"
                        element={
                            <ProtectedRoute>
                                <HomePage />
                            </ProtectedRoute>
                        }
                    />
                    <Route path="*" element={<Navigate to="/login" replace />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
