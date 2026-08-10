import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

// obavija stranice kojima je potrebna prijava - ako korisnik nije ulogovan, preusmerava na login
function ZahtevanaAutentifikacija({ children }) {
  const { korisnik } = useAuth()

  if (!korisnik) {
    return <Navigate to="/prijava" replace />
  }

  return children
}

export default ZahtevanaAutentifikacija
