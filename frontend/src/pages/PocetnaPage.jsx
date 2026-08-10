import { useAuth } from '../context/AuthContext'

function PocetnaPage() {
  const { korisnik, odjaviKorisnika } = useAuth()

  return (
    <div className="forma-kontejner">
      <h1>Dobrodosli u AutoNaj</h1>
      <p>
        Prijavljeni ste kao <strong>{korisnik.email}</strong> ({korisnik.uloga})
      </p>
      <button onClick={odjaviKorisnika}>Odjavi se</button>
    </div>
  )
}

export default PocetnaPage
