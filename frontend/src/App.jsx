import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import ZahtevanaAutentifikacija from './components/ZahtevanaAutentifikacija'
import PrijavaPage from './pages/PrijavaPage'
import RegistracijaPage from './pages/RegistracijaPage'
import PocetnaPage from './pages/PocetnaPage'
import './App.css'

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/prijava" element={<PrijavaPage />} />
          <Route path="/registracija" element={<RegistracijaPage />} />
          <Route
            path="/"
            element={
              <ZahtevanaAutentifikacija>
                <PocetnaPage />
              </ZahtevanaAutentifikacija>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
