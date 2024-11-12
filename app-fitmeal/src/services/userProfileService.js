const { VITE_URL_API } = import.meta.env;
// src/services/userProfileService.js
export const saveUserProfile = async (profileData) => {
    try {
      const response = await fetch(`${VITE_URL_API}/user-profile`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(profileData),
      });
  
      const result = await response.json();
  
      // Si la validación del backend retorna un error
      if (result.error) {
        return result.error; // Retornar el mensaje de error
      }
      return null; // Si no hay error
    } catch (error) {
      console.error('Error al guardar el perfil:', error);
      return 'Hubo un error al guardar los datos. Por favor, inténtalo nuevamente.';
    }
  };  