import { useEffect, useState } from 'react'
import { getSviAutomobili } from '../api/automobilApi'
import { kreirajRezervaciju } from '../api/rezervacijaApi'
import { useAuth } from '../context/AuthContext'
import Navigacija from '../components/Navigacija'

function AutomobiliPage() {
  const { korisnik } = useAuth()
  const [automobili, setAutomobili] = useState([])
  const [greska, setGreska] = useState('')
  const [otvorenZaId, setOtvorenZaId] = useState(null)
  const [datumOd, setDatumOd] = useState('')
  const [datumDo, setDatumDo] = useState('')
  const [porukaZaId, setPorukaZaId] = useState({})

  useEffect(() => {
    ucitajAutomobile()
  }, [])

  function ucitajAutomobile() {
    getSviAutomobili()
      .then(setAutomobili)
      .catch(() => setGreska('Neuspesno ucitavanje automobila'))
  }

  function otvoriFormu(automobilId) {
    setOtvorenZaId(automobilId)
    setDatumOd('')
    setDatumDo('')
    setPorukaZaId((prethodno) => ({ ...prethodno, [automobilId]: null }))
  }

  async function posaljiRezervaciju(automobilId) {
    try {
      const rezervacija = await kreirajRezervaciju({
        korisnikId: Number(korisnik.korisnikId),
        automobilId,
        datumOd,
        datumDo,
      })
      setPorukaZaId((prethodno) => ({
        ...prethodno,
        [automobilId]: {
          tip: 'uspeh',
          tekst: `Rezervacija uspesna. Ukupna cena: ${rezervacija.ukupnaCena} EUR (status: ${rezervacija.status})`,
        },
      }))
      setOtvorenZaId(null)
    } catch (err) {
      setPorukaZaId((prethodno) => ({
        ...prethodno,
        [automobilId]: {
          tip: 'greska',
          tekst: err.response?.data?.poruka || 'Rezervacija nije uspela',
        },
      }))
    }
  }

  return (
    <div>
      <Navigacija />
      <div className="lista-kontejner">
        <h1>Automobili</h1>
        {greska && <p className="greska">{greska}</p>}
        <div className="automobili-lista">
          {automobili.map((automobil) => (
            <div className="automobil-kartica" key={automobil.id}>
              <h3>
                {automobil.marka} {automobil.model} ({automobil.godiste})
              </h3>
              <p>Kategorija: {automobil.kategorijaNaziv}</p>
              <p>Filijala: {automobil.filijalaNaziv}</p>
              <p>Cena po danu: {automobil.cenaPoDanu} EUR</p>
              <p>{automobil.dostupan ? 'Dostupan' : 'Trenutno nedostupan'}</p>

              {porukaZaId[automobil.id] && (
                <p className={porukaZaId[automobil.id].tip === 'greska' ? 'greska' : 'uspeh'}>
                  {porukaZaId[automobil.id].tekst}
                </p>
              )}

              {otvorenZaId === automobil.id ? (
                <div className="rezervacija-forma">
                  <label>
                    Od
                    <input type="date" value={datumOd} onChange={(e) => setDatumOd(e.target.value)} required />
                  </label>
                  <label>
                    Do
                    <input type="date" value={datumDo} onChange={(e) => setDatumDo(e.target.value)} required />
                  </label>
                  <button onClick={() => posaljiRezervaciju(automobil.id)}>Potvrdi rezervaciju</button>
                  <button onClick={() => setOtvorenZaId(null)}>Otkazi</button>
                </div>
              ) : (
                <button disabled={!automobil.dostupan} onClick={() => otvoriFormu(automobil.id)}>
                  Rezervisi
                </button>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}

export default AutomobiliPage
