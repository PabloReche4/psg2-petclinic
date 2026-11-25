import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import './global.css';
import '@splidejs/react-splide/css/sea-green';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import './static/css/tableHeaders.css';
import { BrowserRouter } from 'react-router-dom';
import { SpaceProvider } from 'space-react-client';

const root = ReactDOM.createRoot(document.getElementById('root'));
const spaceConfig = {
  url : "https://oitilo.us.es/space",
  apiKey: 'org_4517a15babc06b04ebeb85336c3d9422ed778875d27c5b49cfe05196f1113af3',
  allowConnectionWithSpace: true,
};
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <SpaceProvider config={spaceConfig}>
        <App />
      </SpaceProvider>
    </BrowserRouter >
  </React.StrictMode>
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
