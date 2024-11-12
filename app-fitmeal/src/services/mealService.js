const { VITE_URL_API } = import.meta.env;



export const fetchDailyMeals = async (userId) => {
    const token = localStorage.getItem('token');
    try {
      const response = await fetch(`${VITE_URL_API}/meal-plan/daily-meals/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error('Failed to fetch daily meals');
      }
      
      const data = await response.json();
      
      return data;
    } catch (error) {
      console.error('Fetch Daily Meals Error:', error);
      return null;
    }
  };
  

  export const fetchWeeklyMeals = async (userId) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(`${VITE_URL_API}/meal-plan/weekly-meals/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Failed to fetch weekly meals');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Fetch Weekly Meals Error:', error);
        return null;
    }
};
