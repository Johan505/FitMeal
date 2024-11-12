import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ProfileCompletionPage from './pages/ProfileCompletionPage';
import HomePage from './pages/HomePage';  // Asegúrate de importar HomePage correctamente
import DietSelectionPage from './pages/PreferencePage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/complete-profile"
          element={ <ProfileCompletionPage /> }
        />
        <Route path="/home" element={ <HomePage />} />
        <Route path="/select-preference" element={<DietSelectionPage />} />
        <Route path="/" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;