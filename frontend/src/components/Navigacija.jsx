import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function Navigacija() {
  const { korisnik, odjaviKorisnika } = useAuth()

  return (
    <nav className="navigacija">
      <Link to="/">AutoNaj</Link>
      <Link to="/automobili">Automobili</Link>
      {korisnik.uloga === 'ROLE_ADMIN' && <Link to="/admin">Admin panel</Link>}
      <span className="navigacija-korisnik">
        {korisnik.email} ({korisnik.uloga})
      </span>
      <button onClick={odjaviKorisnika}>Odjavi se</button>
    </nav>
  )
}

export default Navigacija
