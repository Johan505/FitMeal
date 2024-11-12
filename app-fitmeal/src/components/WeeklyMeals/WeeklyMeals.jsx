import { useEffect, useState } from 'react';
import { fetchWeeklyMeals } from '../../services/mealService';

const WeeklyMeals = () => {
  const [meals, setMeals] = useState([]);
  const [expandedMealIndex, setExpandedMealIndex] = useState(null); // Estado para expandir una tarjeta específica
  const userId = localStorage.getItem('userid'); // Obtener userId desde localStorage

  useEffect(() => {
    const fetchData = async () => {
      const data = await fetchWeeklyMeals(userId);
      setMeals(data);
    };
    
    fetchData();
  }, []);

  // Agrupar comidas por día de la semana y luego por tipo de comida (desayuno, almuerzo, etc.)
  const groupedMeals = meals.reduce((acc, meal) => {
    const day = meal.dayOfWeek;
    if (!acc[day]) {
      acc[day] = {};
    }
    if (!acc[day][meal.mealType]) {
      acc[day][meal.mealType] = [];
    }
    acc[day][meal.mealType].push(meal);
    return acc;
  }, {});

  return (
    <div style={{ padding: '20px' }}>
      <h2 style={{ color: 'black' }}>Comidas Semanales</h2>
      {Object.keys(groupedMeals).map((day) => (
        <div key={day} style={{ marginBottom: '20px' }}>
          <h3 style={{ color: 'black', marginBottom: '10px' }}>{day}</h3> {/* Título del día de la semana */}
          {Object.keys(groupedMeals[day]).map((mealType) => (
            <div key={mealType} style={{ marginBottom: '20px' }}>
              <h4 style={{ color: 'black', marginBottom: '10px' }}>{mealType}</h4> {/* Subtítulo del tipo de comida */}
              <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                {groupedMeals[day][mealType].map((meal, index) => (
                  <div
                    key={index}
                    onClick={() => setExpandedMealIndex(expandedMealIndex === index ? null : index)} // Expande o colapsa la tarjeta
                    style={{
                      display: 'flex',
                      flexDirection: expandedMealIndex === index ? 'column' : 'row', // Ajusta la dirección cuando está expandida
                      alignItems: 'center',
                      padding: '10px',
                      boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
                      borderRadius: '8px',
                      backgroundColor: '#fff',
                      cursor: 'pointer',
                      transition: 'all 0.3s ease',
                    }}
                  >
                    <img
                      src={`https://via.placeholder.com/80`} // Imagen de ejemplo
                      alt={meal.name}
                      style={{
                        borderRadius: '8px',
                        width: '80px',
                        height: '80px',
                        marginRight: '15px',
                        marginBottom: expandedMealIndex === index ? '10px' : '0',
                      }}
                    />
                    <div>
                      <h4 style={{ margin: '0 0 5px', color: 'black' }}>{meal.name}</h4>
                      <p style={{ margin: 0, color: 'black' }}>{meal.calories} calorías</p>
                      
                      {expandedMealIndex === index && (
                        <div style={{ marginTop: '10px', color: 'black' }}>
                          <p>Proteína: {meal.protein}g</p>
                          <p>Grasa: {meal.fat}g</p>
                          <p>Carbohidratos: {meal.carbs}g</p>
                          <p>Tamaño de la porción: {meal.servingsize}</p>
                        </div>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
};

export default WeeklyMeals;