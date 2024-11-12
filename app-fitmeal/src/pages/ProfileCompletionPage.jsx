import { useState } from 'react';
import { completeProfile } from '../services/profileService';
import { validateProfile } from '../services/profileValidationService';
import { useNavigate } from 'react-router';
import "./styles/profileComplete.css"

const ProfileCompletionPage = () => {
  const navigate = useNavigate(); // Hook para navegar entre páginas
  const [formData, setFormData] = useState({
    weight: '',
    height: '',
    goalId: '',
    sex: '',
    age: '',
    activityLevel: '',
  });

  const [formErrors, setFormErrors] = useState({});
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async () => {
    const errors = validateProfile(formData);

    if (Object.keys(errors).length > 0) {
      setFormErrors(errors);
      return; // No enviamos el formulario si hay errores
    }

    setError('');
    try {
      // Consolidar los datos en un solo objeto antes de enviarlos
      const formDataWithNumericValues = {
        ...formData,
        goalId: parseInt(formData.goalId, 10), // Convertir goalId a número
        activityLevel: parseInt(formData.activityLevel, 10), // Convertir activityLevel a número
      };

      const result = await completeProfile(formDataWithNumericValues);
      if (result) {
        localStorage.setItem('profileComplete', 'true');
        navigate('/select-preference'); // Redirige al siguiente paso
      } else {
        setError('Hubo un error al completar tu perfil. Por favor, intenta nuevamente.');
      }
    } catch (err) {
      setError('Ocurrió un error. Por favor, intenta nuevamente.', err);
    }
  };

  return (
    <div className="container-form">
      <div className='content form'>

      <div className='info-form'>

      <h2>Completa tu perfil</h2>
      <p>Para ofrecerte un plan de dieta personalizado y ajustado a tus objetivos, necesitamos algunos datos adicionales. ¡Es rápido y fácil!</p>
      </div>

      <form onSubmit={(e) => e.preventDefault()} className='form-complete-profile'>
          <label>Peso:</label>
          <input
            type="number"
            name="weight"
            placeholder="Peso (kg)"
            value={formData.weight}
            onChange={handleChange}
            required
            className='input-cprofile'
          />
          {formErrors.weight && <p className="error-message">{formErrors.weight}</p>}

          <label>Altura:</label>
          <input
            type="number"
            name="height"
            placeholder="Altura (m)"
            value={formData.height}
            onChange={handleChange}
            required
             className='input-cprofile'
          />
          {formErrors.height && <p className="error-message">{formErrors.height}</p>}
      
          <label>Sexo:</label>
          <select name="sex" value={formData.sex} onChange={handleChange} required  className='input-cprofile'>
            <option value="">Selecciona tu sexo</option>
            <option value="male">Masculino</option>
            <option value="female">Femenino</option>
            <option value="other">Prefiero no decirlo</option>
          </select>

          <label>Objetivo:</label>
          <select name="goalId" value={formData.goalId} onChange={handleChange} required  className='input-cprofile'>
            <option value="">Selecciona tu objetivo</option>
            <option value="1">Perder peso</option>
            <option value="2">Ganar peso</option>
            <option value="3">Mantener peso</option>
            <option value="4">Ganar músculo</option>
            <option value="5">Definición</option>
            <option value="6">Alta proteína</option>
          </select>
          <label>Edad:</label>
          <input
            type="number"
            name="age"
            placeholder="Edad"
            value={formData.age}
            onChange={handleChange}
            required
             className='input-cprofile'
          />
          {formErrors.age && <p className="error-message">{formErrors.age}</p>}
          <label>Actividad:</label>
          <select name="activityLevel" value={formData.activityLevel} onChange={handleChange} required  className='input-cprofile'>
            <option value="">Selecciona tu nivel de actividad</option>
            <option value="1">1 - Sedentario</option>
            <option value="2">2 - Ligeramente activo</option>
            <option value="3">3 - Moderadamente activo</option>
            <option value="4">4 - Muy activo</option>
            <option value="5">5 - Extremadamente activo</option>
          </select>

          {formErrors.activityLevel && <p className="error-message">{formErrors.activityLevel}</p>}


        <button onClick={handleSubmit} className="btn-cprofile">
          Continuar
        </button>
      </form>
      {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default ProfileCompletionPage;