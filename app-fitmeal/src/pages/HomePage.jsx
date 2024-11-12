import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../components/Sidebar/Sidebar";
import DailyMeals from '../components/DailyMeals/DailyMeals';
import WeeklyMeals from "../components/WeeklyMeals/WeeklyMeals";
import { fetchUserProfile } from "../services/profileService"; // Importamos el servicio para obtener el perfil del usuario

const HomePage = () => {
  const navigate = useNavigate();
  const user = localStorage.getItem('lastname') || "Usuario";
  const userId = localStorage.getItem('userid');

  // Estado para manejar la selección de "Comida Diaria" o "Comida Semanal"
  const [view, setView] = useState(null);
  const [caloriesNeeded, setCaloriesNeeded] = useState(null); // Estado para almacenar las calorías necesarias

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login"); // Si no hay token, redirigir al login
    }
  }, [navigate]);

  useEffect(() => {
    const getUserProfile = async () => {
      try {
        const profileData = await fetchUserProfile(userId);
        setCaloriesNeeded(profileData.caloriesNeeded); // Asignar las calorías necesarias desde el perfil del usuario
      } catch (error) {
        console.error("Error al obtener el perfil de usuario:", error);
      }
    };

    getUserProfile();
  }, [userId]);

  return (
    <div className="home-page">
      <Sidebar />
      <div className="main-content">
        <header className="header">
          <h1>¡Bienvenido a tu plan personalizado!</h1>
          <div className="user-info">
            <span className="user-name">Hola, {user}</span>
          </div>
          {/* Mostrar calorías necesarias */}
          {caloriesNeeded && (
            <p>
              Necesitas consumir {caloriesNeeded} calorías al día
            </p>
          )}
        </header>
        
        <section className="options">
          <h2>Comidas recomendadas para ti</h2>
          <div style={{ display: 'flex', gap: '20px', marginTop: '10px' }}>
            <button 
              onClick={() => setView('daily')}
              style={{
                padding: '10px 20px',
                cursor: 'pointer',
                backgroundColor: view === 'daily' ? '#d15e0d' : '#f0f0f0',
                color: view === 'daily' ? 'white' : 'black',
                borderRadius: '8px',
                border: 'none'
              }}
            >
              Comida Diaria
            </button>
            <button 
              onClick={() => setView('weekly')}
              style={{
                padding: '10px 20px',
                cursor: 'pointer',
                backgroundColor: view === 'weekly' ? '#d15e0d' : '#f0f0f0',
                color: view === 'weekly' ? 'white' : 'black',
                borderRadius: '8px',
                border: 'none'
              }}
            >
              Comida Semanal
            </button>
          </div>
        </section>

        {/* Renderizado condicional de DailyMeals o WeeklyMeals */}
        {view === 'daily' && (
          <section className="recommendations">
            <DailyMeals/>
          </section>
        )}
        {view === 'weekly' && (
          <section className="recommendations">
            <WeeklyMeals/>
          </section>
        )}
      </div>
    </div>
  );
};

export default HomePage;