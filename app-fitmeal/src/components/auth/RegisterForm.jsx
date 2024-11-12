import { useState } from 'react';
import { register } from '../../services/registerService';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { validatePasswordRequirements, validateFormFields } from '../../utils/validations.js';

const RegisterForm = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPasswordRequirements, setShowPasswordRequirements] = useState(false);
  const [showConfirmPasswordMessage, setShowConfirmPasswordMessage] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async () => {
    setError('');

    const fields = { firstName, lastName, email, password, confirmPassword };
    const fieldErrors = validateFormFields(fields);
    const passwordRequirements = validatePasswordRequirements(password);

    if (Object.keys(fieldErrors).length > 0) {
      setError(Object.values(fieldErrors).join('. '));
      return;
    }

    if (!passwordRequirements.length || !passwordRequirements.hasNumberOrUppercase || !passwordRequirements.noSpaces) {
      setError('Por favor, cumple con todos los requisitos de la contraseña.');
      return;
    }

    try {
      const result = await register(firstName, lastName, email, password);
      if (result) {
        Swal.fire({
          icon: 'success',
          title: 'Registro exitoso',
          text: '¡Ahora puedes iniciar sesión!',
          confirmButtonText: 'Aceptar',
          confirmButtonColor: '#d15e0d' // Cambia el color del botón
        }).then(() => {
          navigate('/login');
        });
      } else {
        setError('Falló el registro. Inténtalo de nuevo.');
      }
    } catch (err) {
      setError('Ocurrió un error. Inténtalo de nuevo.', err);
    }
  };

  const passwordRequirements = validatePasswordRequirements(password);
  const allRequirementsMet = passwordRequirements.length && passwordRequirements.hasNumberOrUppercase && passwordRequirements.noSpaces;

  return (
    <div className='container-input-register'>
      <label>Nombre</label>
      <input
        type="text"
        placeholder="Nombre"
        value={firstName}
        onChange={(e) => setFirstName(e.target.value)}
        className='form-input-register'
      />
      <label>Apellido</label>
      <input
        type="text"
        placeholder="Apellido"
        value={lastName}
        onChange={(e) => setLastName(e.target.value)}
        className='form-input-register'
      />
      <label>Correo</label>
      <input
        type="email"
        placeholder="Correo Electrónico"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        className='form-input-register'
      />
      <label>Contraseña</label>
      <input
        type="password"
        placeholder="Contraseña"
        value={password}
        onChange={(e) => {
          setPassword(e.target.value);
          if (!showPasswordRequirements) {
            setShowPasswordRequirements(true);
          }
        }}
        className='form-input-register'
      />
      {showPasswordRequirements && (
        <ul>
          <li style={{ color: passwordRequirements.length ? 'green' : 'red' }}>
            {passwordRequirements.length ? '✔️' : '❌'} Al menos 6 caracteres
          </li>
          <li style={{ color: passwordRequirements.hasNumberOrUppercase ? 'green' : 'red' }}>
            {passwordRequirements.hasNumberOrUppercase ? '✔️' : '❌'} Al menos un número o una letra mayúscula
          </li>
          <li style={{ color: passwordRequirements.noSpaces ? 'green' : 'red' }}>
            {passwordRequirements.noSpaces ? '✔️' : '❌'} Sin espacios en blanco
          </li>
        </ul>
      )}
      <label>Confirmar Contraseña</label>
      <input
        type="password"
        placeholder="Confirmar Contraseña"
        value={confirmPassword}
        onChange={(e) => {
          setConfirmPassword(e.target.value);
          setShowConfirmPasswordMessage(true);
        }}
        className='form-input-register'
        disabled={!allRequirementsMet}
      />
      {showConfirmPasswordMessage && (
        <p style={{ color: password === confirmPassword ? 'green' : 'red' }}>
          {password === confirmPassword ? '✔️ Las contraseñas coinciden' : '❌ Las contraseñas no coinciden'}
        </p>
      )}
      <button className='btn-register' onClick={handleSubmit}>
        Registrate
      </button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default RegisterForm;
