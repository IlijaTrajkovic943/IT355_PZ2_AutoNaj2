import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { prijavi } from '../api/authApi'
import { useAuth } from '../context/AuthContext'

function PrijavaPage() {
  const [email, setEmail] = useState('')
  const [lozinka, setLozinka] = useState('')
  const [greska, setGreska] = useState('')
  const { prijaviKorisnika } = useAuth()
  const navigate = useNavigate()

  async function posaljiFormu(e) {
    e.preventDefault()
    setGreska('')
    try {
      const odgovor = await prijavi(email, lozinka)
      prijaviKorisnika(odgovor)
      navigate('/')
    } catch (err) {
      setGreska(err.response?.data?.poruka || 'Prijava nije uspela')
    }
  }

  return (
    <div className="forma-kontejner">
      <h1>Prijava</h1>
      <form onSubmit={posaljiFormu}>
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
        <button type="submit">Prijavi se</button>
      </form>
      <p>
        Nemate nalog? <Link to="/registracija">Registrujte se</Link>
      </p>
    </div>
  )
}

export default PrijavaPage
