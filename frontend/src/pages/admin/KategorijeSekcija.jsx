import { useEffect, useState } from 'react'
import { getSveKategorije, kreirajKategoriju, obrisiKategoriju } from '../../api/kategorijaApi'

function KategorijeSekcija() {
  const [kategorije, setKategorije] = useState([])
  const [naziv, setNaziv] = useState('')
  const [opis, setOpis] = useState('')
  const [greska, setGreska] = useState('')

  useEffect(() => {
    ucitaj()
  }, [])

  function ucitaj() {
    getSveKategorije().then(setKategorije)
  }

  async function dodaj(e) {
    e.preventDefault()
    setGreska('')
    try {
      await kreirajKategoriju({ naziv, opis })
      setNaziv('')
      setOpis('')
      ucitaj()
    } catch (err) {
      setGreska(err.response?.data?.poruka || 'Kreiranje nije uspelo')
    }
  }

  async function obrisi(id) {
    await obrisiKategoriju(id)
    ucitaj()
  }

  return (
    <section className="admin-sekcija">
      <h2>Kategorije vozila</h2>
      <ul>
        {kategorije.map((k) => (
          <li key={k.id}>
            {k.naziv} - {k.opis}
            <button onClick={() => obrisi(k.id)}>Obrisi</button>
          </li>
        ))}
      </ul>
      <form onSubmit={dodaj} className="admin-forma">
        <input placeholder="Naziv" value={naziv} onChange={(e) => setNaziv(e.target.value)} required />
        <input placeholder="Opis" value={opis} onChange={(e) => setOpis(e.target.value)} />
        <button type="submit">Dodaj kategoriju</button>
      </form>
      {greska && <p className="greska">{greska}</p>}
    </section>
  )
}

export default KategorijeSekcija
