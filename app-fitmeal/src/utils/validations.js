// src/utils/validation.js

export const validatePasswordRequirements = (password) => {
    return {
      length: password.length >= 6,
      hasNumberOrUppercase: /[A-Z0-9]/.test(password),
      noSpaces: !/\s/.test(password),
    };
  };
  
  export const validateFormFields = (fields) => {
    const errors = {};
  
    if (!fields.firstName) {
      errors.firstName = 'El nombre es obligatorio';
    }
    if (!fields.lastName) {
      errors.lastName = 'El apellido es obligatorio';
    }
    if (!fields.email) {
      errors.email = 'El correo electrónico es obligatorio';
    } else if (!/\S+@\S+\.\S+/.test(fields.email)) {
      errors.email = 'Formato de correo electrónico no válido';
    }
    if (!fields.password) {
      errors.password = 'La contraseña es obligatoria';
    }
    if (!fields.confirmPassword) {
      errors.confirmPassword = 'La confirmación de la contraseña es obligatoria';
    } else if (fields.password !== fields.confirmPassword) {
      errors.confirmPassword = 'Las contraseñas no coinciden';
    }
  
    return errors;
  };  