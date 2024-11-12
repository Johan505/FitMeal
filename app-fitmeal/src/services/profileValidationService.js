// src/services/profileValidationService.js
export const validateProfile = ({ weight, height, age, activityLevel }) => {
    const errors = {};
  
    // Validar peso
    if (weight < 30 || weight > 200) {
      errors.weight = 'El peso debe estar entre 30 y 200 kg.';
    }
  
    // Validar altura
    if (height < 1.0 || height > 2.5) {
      errors.height = 'La altura debe estar entre 1.0 y 2.5 metros.';
    }
  
    // Validar edad
    if (age < 18 || age > 100) {
      errors.age = 'La edad debe estar entre 18 y 100 años.';
    }
  
    // Validar nivel de actividad
    if (activityLevel < 1 || activityLevel > 5) {
      errors.activityLevel = 'El nivel de actividad debe estar entre 1 y 5.';
    }
  
    return errors;
  };  