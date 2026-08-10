import { createContext, useContext, useState } from 'react'

const AuthContext = createContext(null)

// AuthProvider drzi podatke o prijavljenom korisniku i cuva ih u localStorage
// da korisnik ostane prijavljen i posle osvezavanja stranice
export function AuthProvider({ children }) {
  const [korisnik, setKorisnik] = useState(() => {
    const token = localStorage.getItem('token')
    if (!token) {
      return null
    }
    return {
      token,
      email: localStorage.getItem('email'),
      uloga: localStorage.getItem('uloga'),
    }
  })

  function prijaviKorisnika(odgovor) {
    localStorage.setItem('token', odgovor.token)
    localStorage.setItem('email', odgovor.email)
    localStorage.setItem('uloga', odgovor.uloga)
    setKorisnik({ token: odgovor.token, email: odgovor.email, uloga: odgovor.uloga })
  }

  function odjaviKorisnika() {
    localStorage.removeItem('token')
    localStorage.removeItem('email')
    localStorage.removeItem('uloga')
    setKorisnik(null)
  }

  return (
    <AuthContext.Provider value={{ korisnik, prijaviKorisnika, odjaviKorisnika }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
