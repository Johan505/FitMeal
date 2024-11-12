import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { saveUserDietPreference, assignMealPlan } from '../services/profileService';
import "./styles/preferencePage.css";

function DietSelectionPage() {
  const [selectedDiet, setSelectedDiet] = useState("");
  const navigate = useNavigate();

  const handleSelection = (diet) => {
    setSelectedDiet(diet);
  };

  const handleSaveProfile = async () => {
    const userId = localStorage.getItem('userid');
    if (!userId) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'No se encontró el userId en el almacenamiento local.',
        confirmButtonColor: '#d15e0d',
      });
      return;
    }

    if (!selectedDiet) {
      Swal.fire({
        icon: 'warning',
        title: 'Advertencia',
        text: 'Seleccione una dieta antes de guardar.',
        confirmButtonColor: '#d15e0d',
      });
      return;
    }
    
    try {
      // Guardar la preferencia de dieta del usuario
      const preferenceResponse = await saveUserDietPreference(userId, selectedDiet);
      console.log("Preferencia de dieta guardada:", preferenceResponse);

      // Asignar el plan de dieta al usuario
      const assignResponse = await assignMealPlan(userId);
      console.log("Asignación de plan de dieta exitosa:", assignResponse);

      Swal.fire({
        icon: 'success',
        title: '¡Éxito!',
        text: 'Preferencia de dieta guardada y plan de dieta asignado.',
        confirmButtonColor: '#d15e0d',
      }).then(() => {
        navigate('/home');
      });
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Ocurrió un error al guardar la preferencia de dieta o asignar el plan.',
        confirmButtonColor: '#d15e0d',
      });
      console.error("Error en el proceso de guardar preferencia de dieta y asignar plan:", error);
    }
  };

  return (
    <div style={{ padding: '20px', textAlign: 'center', color: '#d15e0d' }}>
      <h1>Seleccione una opción de dieta</h1>
      
      <div style={{ display: 'block', marginTop: '20px' }}>
        <div 
          onClick={() => handleSelection(1)} className='target'
        >
          <img src="https://ingenieriademenu.com/wp-content/uploads/2022/05/Cuales-son-las-frutas-y-cuales-son-las-verduras.jpg" alt="Económica" />
          <h4>Económica</h4>
          <p>Opciones de dieta asequibles</p>
        </div>

        <div 
          onClick={() => handleSelection(2)} className='target'
        >
          <img src="https://www.miarevista.es/wp-content/uploads/sites/3/2024/09/06/66dacc7fd5284.png" alt="Familiar" />
          <h4>Familiar</h4>
          <p>Opciones de dieta para compartir</p>
        </div>

        <div 
          onClick={() => handleSelection(3)} className='target'
        >
          <img src="https://www.elespectador.com/resizer/0RBE43of1FIVBguCBHnF_sRCAKE=/arc-anglerfish-arc2-prod-elespectador/public/CZT2L7SIH5H3RJ5DH5JE2QHHVE.jpg" alt="Nutritiva" />
          <h4>Nutritiva</h4>
          <p>Opciones de dieta enfocadas en salud</p>
        </div>
      </div>

      <div style={{ marginTop: '30px' }}>
        <button 
          onClick={handleSaveProfile}
          disabled={!selectedDiet}
          className='btn-preference'
        >
          Guardar perfil
        </button>
      </div>
    </div>
  );
}

export default DietSelectionPage;