import RegisterForm from "../components/auth/RegisterForm";
import { Link } from "react-router-dom";
import "./styles/register.css"

const RegisterPage = () => {
  return (
    <div className="container-register">
      <div className="sign-up">
        <div className="title">
          <h1>Registrate</h1>
        </div>
        <RegisterForm />
        <p className="to-login">
          ¿Ya tienes una cuenta? <Link to="/login" className="redirect-to-login">Inicia Sesión</Link>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;
