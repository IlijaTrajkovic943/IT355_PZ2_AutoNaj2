import { useEffect, useState } from 'react'
import { getSveFilijale, kreirajFilijalu, obrisiFilijalu } from '../../api/filijalaApi'

function FilijaleSekcija() {
  const [filijale, setFilijale] = useState([])
  const [naziv, setNaziv] = useState('')
  const [grad, setGrad] = useState('')
  const [adresa, setAdresa] = useState('')
  const [greska, setGreska] = useState('')

  useEffect(() => {
    ucitaj()
  }, [])

  function ucitaj() {
    getSveFilijale().then(setFilijale)
  }

  async function dodaj(e) {
    e.preventDefault()
    setGreska('')
    try {
      await kreirajFilijalu({ naziv, grad, adresa })
      setNaziv('')
      setGrad('')
      setAdresa('')
      ucitaj()
    } catch (err) {
      setGreska(err.response?.data?.poruka || 'Kreiranje nije uspelo')
    }
  }

  async function obrisi(id) {
    await obrisiFilijalu(id)
    ucitaj()
  }

  return (
    <section className="admin-sekcija">
      <h2>Filijale</h2>
      <ul>
        {filijale.map((f) => (
          <li key={f.id}>
            {f.naziv} - {f.grad}, {f.adresa}
            <button onClick={() => obrisi(f.id)}>Obrisi</button>
          </li>
        ))}
      </ul>
      <form onSubmit={dodaj} className="admin-forma">
        <input placeholder="Naziv" value={naziv} onChange={(e) => setNaziv(e.target.value)} required />
        <input placeholder="Grad" value={grad} onChange={(e) => setGrad(e.target.value)} required />
        <input placeholder="Adresa" value={adresa} onChange={(e) => setAdresa(e.target.value)} required />
        <button type="submit">Dodaj filijalu</button>
      </form>
      {greska && <p className="greska">{greska}</p>}
    </section>
  )
}

export default FilijaleSekcija
