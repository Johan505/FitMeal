const { VITE_URL_API } = import.meta.env;

export const register = async (firstName, lastName, email, password) => {
  try {
    const response = await fetch(`${VITE_URL_API}/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ firstName, lastName, email, password }), // Asegúrate de que los nombres de las claves coincidan con los esperados por el backend
    });

    if (!response.ok) {
      throw new Error('Registration failed');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Registration Error:', error);
    return null;
  }
};