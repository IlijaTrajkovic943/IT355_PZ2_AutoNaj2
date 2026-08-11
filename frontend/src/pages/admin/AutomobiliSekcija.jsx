import { useEffect, useState } from 'react'
import { getSviAutomobili, kreirajAutomobil, obrisiAutomobil } from '../../api/automobilApi'
import { getSveKategorije } from '../../api/kategorijaApi'
import { getSveFilijale } from '../../api/filijalaApi'

const PRAZNA_FORMA = {
  marka: '',
  model: '',
  godiste: '',
  registarskiBroj: '',
  cenaPoDanu: '',
  dostupan: true,
  kategorijaId: '',
  filijalaId: '',
}

function AutomobiliSekcija() {
  const [automobili, setAutomobili] = useState([])
  const [kategorije, setKategorije] = useState([])
  const [filijale, setFilijale] = useState([])
  const [forma, setForma] = useState(PRAZNA_FORMA)
  const [greska, setGreska] = useState('')

  useEffect(() => {
    ucitaj()
    getSveKategorije().then(setKategorije)
    getSveFilijale().then(setFilijale)
  }, [])

  function ucitaj() {
    getSviAutomobili().then(setAutomobili)
  }

  function izmeniPolje(polje, vrednost) {
    setForma((prethodna) => ({ ...prethodna, [polje]: vrednost }))
  }

  async function dodaj(e) {
    e.preventDefault()
    setGreska('')
    try {
      await kreirajAutomobil({
        ...forma,
        godiste: Number(forma.godiste),
        cenaPoDanu: Number(forma.cenaPoDanu),
        kategorijaId: Number(forma.kategorijaId),
        filijalaId: Number(forma.filijalaId),
      })
      setForma(PRAZNA_FORMA)
      ucitaj()
    } catch (err) {
      setGreska(err.response?.data?.poruka || 'Kreiranje nije uspelo')
    }
  }

  async function obrisi(id) {
    await obrisiAutomobil(id)
    ucitaj()
  }

  return (
    <section className="admin-sekcija">
      <h2>Automobili</h2>
      <ul>
        {automobili.map((a) => (
          <li key={a.id}>
            {a.marka} {a.model} ({a.registarskiBroj}) - {a.kategorijaNaziv}, {a.filijalaNaziv}
            <button onClick={() => obrisi(a.id)}>Obrisi</button>
          </li>
        ))}
      </ul>
      <form onSubmit={dodaj} className="admin-forma">
        <input
          placeholder="Marka"
          value={forma.marka}
          onChange={(e) => izmeniPolje('marka', e.target.value)}
          required
        />
        <input
          placeholder="Model"
          value={forma.model}
          onChange={(e) => izmeniPolje('model', e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Godiste"
          value={forma.godiste}
          onChange={(e) => izmeniPolje('godiste', e.target.value)}
          required
        />
        <input
          placeholder="Registarski broj"
          value={forma.registarskiBroj}
          onChange={(e) => izmeniPolje('registarskiBroj', e.target.value)}
          required
        />
        <input
          type="number"
          step="0.01"
          placeholder="Cena po danu"
          value={forma.cenaPoDanu}
          onChange={(e) => izmeniPolje('cenaPoDanu', e.target.value)}
          required
        />
        <select
          value={forma.kategorijaId}
          onChange={(e) => izmeniPolje('kategorijaId', e.target.value)}
          required
        >
          <option value="">Kategorija</option>
          {kategorije.map((k) => (
            <option key={k.id} value={k.id}>
              {k.naziv}
            </option>
          ))}
        </select>
        <select value={forma.filijalaId} onChange={(e) => izmeniPolje('filijalaId', e.target.value)} required>
          <option value="">Filijala</option>
          {filijale.map((f) => (
            <option key={f.id} value={f.id}>
              {f.naziv}
            </option>
          ))}
        </select>
        <label className="checkbox-label">
          <input
            type="checkbox"
            checked={forma.dostupan}
            onChange={(e) => izmeniPolje('dostupan', e.target.checked)}
          />
          Dostupan
        </label>
        <button type="submit">Dodaj automobil</button>
      </form>
      {greska && <p className="greska">{greska}</p>}
    </section>
  )
}

export default AutomobiliSekcija
