import { useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // Usamos useNavigate para redirigir
import { logout } from "../../services/authService"; // Correcta importación de la función logout
import './Sidebar.css';  // Importar el CSS relativo al archivo Sidebar.jsx

const Sidebar = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const navigate = useNavigate(); // Hook para navegar

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className={`sidebar-container ${isSidebarOpen ? "open" : ""}`}>
      {/* Sidebar */}
      <div className="sidebar">
        <div className="sidebar-header">
          {/* Logo de Usuario */}
          <div className="user-logo">
            <p className="user-name">Usuario</p>
          </div>

          <button className="toggle-btn" onClick={toggleSidebar}>
            {isSidebarOpen ? "Close" : "Open"}
          </button>
        </div>

        {/* Opciones del Sidebar */}
        <div className="sidebar-content">
          <ul className="sidebar-options">
            <li>
              <Link to="/profile">Perfil</Link>
            </li>
            <li>
              <Link to="/settings">Ajustes</Link>
            </li>
            <li>
              <Link to="/help">Ayuda</Link>
            </li>
          </ul>

          {/* Botón de Cerrar sesión */}
          <button className="button-logout" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
