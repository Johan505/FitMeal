import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LoginForm from "../components/auth/LoginForm";
import { Link } from "react-router-dom";
import "./styles/login.css";

const LoginPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const authComplete = localStorage.getItem("token");
    if (authComplete) {
      navigate("/home");
    }
  }, [navigate]);

  return (
    <div className="container-login">
      <div className="login">
        <div className="title">
          <h1>Iniciar Sesión</h1>
        </div>
        <LoginForm />
        <p className="to-register">
          ¿No tienes una cuenta? <Link to="/register" className="redirect-to-register">Regístrate aquí</Link>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
