import http from './http'

export function getSveFilijale() {
  return http.get('/api/filijale').then((odgovor) => odgovor.data)
}

export function kreirajFilijalu(dto) {
  return http.post('/api/filijale', dto).then((odgovor) => odgovor.data)
}

export function obrisiFilijalu(id) {
  return http.delete(`/api/filijale/${id}`)
}
