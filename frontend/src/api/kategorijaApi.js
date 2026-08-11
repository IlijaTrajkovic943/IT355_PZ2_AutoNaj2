import http from './http'

export function getSveKategorije() {
  return http.get('/api/kategorije').then((odgovor) => odgovor.data)
}

export function kreirajKategoriju(dto) {
  return http.post('/api/kategorije', dto).then((odgovor) => odgovor.data)
}

export function obrisiKategoriju(id) {
  return http.delete(`/api/kategorije/${id}`)
}
