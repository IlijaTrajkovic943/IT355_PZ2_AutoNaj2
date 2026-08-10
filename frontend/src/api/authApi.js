import http from './http'

export function prijavi(email, lozinka) {
  return http.post('/auth/prijava', { email, lozinka }).then((odgovor) => odgovor.data)
}

export function registruj(ime, prezime, email, lozinka) {
  return http
    .post('/auth/registracija', { ime, prezime, email, lozinka })
    .then((odgovor) => odgovor.data)
}
