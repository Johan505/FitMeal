import { useState } from 'react';
import { login } from '../../services/authService';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async () => {
    setError('');
    try {
      const result = await login(email, password);
      console.log(result);
    
      if (result && result.token && result.profileComplete === true) {
        navigate('/home');
      }
      if(result && result.token && result.profileComplete === false){
        navigate('/complete-profile')
      } else {
        setError('Correo o contraseña inválidos');
      }
    } catch (err) {
      setError('Ocurrió un error. Inténtalo de nuevo.', err);
    }
  };

  return (
    <div className='container-input-login'>
      <label>Correo</label>
      <input
        type="email"
        placeholder="Correo Electrónico"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        className='form-input'
      />
      <label>Contraseña</label>
      <input
        type="password"
        placeholder="Contraseña"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className='form-input'
      />
      <button className='btn-login' onClick={handleSubmit}>
        Iniciar Sesión
      </button>
      <div>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      </div>
    </div>
  );
};

export default LoginForm;