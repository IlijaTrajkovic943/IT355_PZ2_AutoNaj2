import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { registruj } from '../api/authApi'
import { useAuth } from '../context/AuthContext'

function RegistracijaPage() {
  const [ime, setIme] = useState('')
  const [prezime, setPrezime] = useState('')
  const [email, setEmail] = useState('')
  const [lozinka, setLozinka] = useState('')
  const [greska, setGreska] = useState('')
  const { prijaviKorisnika } = useAuth()
  const navigate = useNavigate()

  async function posaljiFormu(e) {
    e.preventDefault()
    setGreska('')
    try {
      const odgovor = await registruj(ime, prezime, email, lozinka)
      prijaviKorisnika(odgovor)
      navigate('/')
    } catch (err) {
      setGreska(err.response?.data?.poruka || 'Registracija nije uspela')
    }
  }

  return (
    <div className="forma-kontejner">
      <h1>Registracija</h1>
      <form onSubmit={posaljiFormu}>
        <label>
          Ime
          <input type="text" value={ime} onChange={(e) => setIme(e.target.value)} required />
        </label>
        <label>
          Prezime
          <input type="text" value={prezime} onChange={(e) => setPrezime(e.target.value)} required />
        </label>
        <label>
          Email
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </label>
        <label>
          Lozinka
          <input
            type="password"
            value={lozinka}
            onChange={(e) => setLozinka(e.target.value)}
            required
          />
        </label>
        {greska && <p className="greska">{greska}</p>}
        <button type="submit">Registruj se</button>
      </form>
      <p>
        Vec imate nalog? <Link to="/prijava">Prijavite se</Link>
      </p>
    </div>
  )
}

export default RegistracijaPage
