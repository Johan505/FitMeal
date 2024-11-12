const { VITE_URL_API } = import.meta.env;


export const completeProfile = async (profileData) => {
  try {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userid'); // Asumiendo que guardas el userId en localStorage
    if (!userId) {
      throw new Error('User ID is missing');
    }

    const response = await fetch(`${VITE_URL_API}/user-profile/${userId}`, { // Usando el userId en la URL
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(profileData),
    });

    // Verificar la respuesta
    console.log('Response status:', response.status); // Imprimir el código de estado
    console.log('Response body:', await response.text()); // Imprimir el cuerpo de la respuesta

    if (!response.ok) {
      const errorData = await response.json(); // Obtener el mensaje de error
      throw new Error(errorData.message || 'An error occurred while completing the profile');
    }

    return true;
  } catch (error) {
    console.error('Error completing profile:', error);
    return false;
  }
};


export const saveUserDietPreference = async (userId, dietTypeId) => {
  const url = `${VITE_URL_API}/user-diet-preference/${userId}`;
  const payload = { dietTypeId };

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      throw new Error('Error al guardar la preferencia de dieta');
    }

    return response
  } catch (error) {
    console.error(error);
    throw error;
  }
};


export const assignMealPlan = async (userId) => {
  const token = localStorage.getItem('token');
  const url = `${VITE_URL_API}/meal-plan/assign/${userId}`;

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error('Error al asignar el plan de dieta');
    }

    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};



export const fetchUserProfile = async (userId) => {
  const token = localStorage.getItem('token');
  const url = `${VITE_URL_API}/user-profile/${userId}`;

  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error('Error al obtener el perfil de usuario');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
