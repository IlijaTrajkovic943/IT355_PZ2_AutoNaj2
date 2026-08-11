import { useEffect, useState } from 'react'
import { getSveRezervacije, potvrdiRezervaciju, otkaziRezervaciju } from '../../api/rezervacijaApi'

function RezervacijeSekcija() {
  const [rezervacije, setRezervacije] = useState([])

  useEffect(() => {
    ucitaj()
  }, [])

  function ucitaj() {
    getSveRezervacije().then(setRezervacije)
  }

  async function potvrdi(id) {
    await potvrdiRezervaciju(id)
    ucitaj()
  }

  async function otkazi(id) {
    await otkaziRezervaciju(id)
    ucitaj()
  }

  return (
    <section className="admin-sekcija">
      <h2>Rezervacije</h2>
      <ul>
        {rezervacije.map((r) => (
          <li key={r.id}>
            {r.automobilNaziv} - {r.korisnikIme} ({r.datumOd} - {r.datumDo}), {r.ukupnaCena} EUR - {r.status}
            {r.status === 'NA_CEKANJU' && (
              <>
                <button className="dugme-potvrdi" onClick={() => potvrdi(r.id)}>Potvrdi</button>
                <button onClick={() => otkazi(r.id)}>Otkazi</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </section>
  )
}

export default RezervacijeSekcija
