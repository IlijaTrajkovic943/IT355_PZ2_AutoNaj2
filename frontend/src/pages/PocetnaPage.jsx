import { useAuth } from '../context/AuthContext'
import Navigacija from '../components/Navigacija'

function PocetnaPage() {
  const { korisnik } = useAuth()

  return (
    <div>
      <Navigacija />
      <div className="forma-kontejner">
        <h1>Dobrodosli u AutoNaj</h1>
        <p>
          Prijavljeni ste kao <strong>{korisnik.email}</strong> ({korisnik.uloga})
        </p>
        <p>Pogledaj ponudu automobila i napravi rezervaciju u sekciji "Automobili".</p>
      </div>
    </div>
  )
}

export default PocetnaPage
