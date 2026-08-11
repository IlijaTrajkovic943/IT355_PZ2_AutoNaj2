import http from './http'

export function kreirajRezervaciju(dto) {
  return http.post('/api/rezervacije', dto).then((odgovor) => odgovor.data)
}

export function getSveRezervacije() {
  return http.get('/api/rezervacije').then((odgovor) => odgovor.data)
}

export function potvrdiRezervaciju(id) {
  return http.patch(`/api/rezervacije/${id}/potvrdi`).then((odgovor) => odgovor.data)
}

export function otkaziRezervaciju(id) {
  return http.patch(`/api/rezervacije/${id}/otkazi`).then((odgovor) => odgovor.data)
}
