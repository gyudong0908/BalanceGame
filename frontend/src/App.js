import context from 'react-bootstrap/esm/AccordionContext';
import './App.css';
// import MainPage from './page/mainpage';
import MainPage from './page/mainpage';
import { Routes, Route } from 'react-router-dom';
import { GameProvider, UserProvider } from './context';

function App() {
  return (
    <div className="App">
      <GameProvider>
      <UserProvider>
      <Routes>
          <Route path='/' element={<MainPage></MainPage>}/>
      </Routes>
      </UserProvider>
      </GameProvider>
    </div>
  );
}
export default App;
