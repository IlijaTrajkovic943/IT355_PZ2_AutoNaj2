import http from './http'

export function kreirajRezervaciju(dto) {
  return http.post('/api/rezervacije', dto).then((odgovor) => odgovor.data)
}
