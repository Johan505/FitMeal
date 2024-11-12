const { VITE_URL_API } = import.meta.env;

export const login = async (email, password) => {
    try {
      const response = await fetch(`${VITE_URL_API}/auth/token`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const result = await response.json();

  
      if (result.token) {
        // Guardar el token y el estado de profileComplete en localStorage
        localStorage.setItem('token', result.token);
        localStorage.setItem('profileComplete', result.profileComplete.toString());
        localStorage.setItem('userid', result.userid.toString());
        localStorage.setItem('firstname', result.firstname.toString());
        localStorage.setItem('lastname', result.lastname.toString());
        return result;
      }

  
      return false;
    } catch (error) {
      console.error('Error en el login', error);
      return false;
    }
  };  

  export const logout = () => {
    // Eliminar el token y otros datos del localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('profileComplete');
    localStorage.removeItem('userid');
    localStorage.removeItem('firstname');
    localStorage.removeItem('lastname');
  
    // Redirigir al usuario a la página de inicio de sesión o a la home
    // Si usas React Router, puedes usar el hook useNavigate para hacer la redirección
    // Si no, puedes usar window.location.href para hacer la redirección manual
  
    window.location.href = "/login";  // Redirigir a la página de login (ajusta la ruta según tu configuración)
  };
  