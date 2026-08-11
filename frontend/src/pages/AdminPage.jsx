import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import Navigacija from '../components/Navigacija'
import FilijaleSekcija from './admin/FilijaleSekcija'
import KategorijeSekcija from './admin/KategorijeSekcija'
import AutomobiliSekcija from './admin/AutomobiliSekcija'
import RezervacijeSekcija from './admin/RezervacijeSekcija'

function AdminPage() {
  const { korisnik } = useAuth()

  // dodatna provera role - ruta je zasticena samo za ADMIN korisnike
  if (korisnik.uloga !== 'ROLE_ADMIN') {
    return <Navigate to="/" replace />
  }

  return (
    <div>
      <Navigacija />
      <div className="lista-kontejner">
        <h1>Admin panel</h1>
        <RezervacijeSekcija />
        <FilijaleSekcija />
        <KategorijeSekcija />
        <AutomobiliSekcija />
      </div>
    </div>
  )
}

export default AdminPage
