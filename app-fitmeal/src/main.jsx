import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { setupIonicReact } from '@ionic/react';
import './index.css';
import App from './App.jsx';

setupIonicReact(); // Configura Ionic para React

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>
);