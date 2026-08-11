import http from './http'

export function getSviAutomobili() {
  return http.get('/api/automobili').then((odgovor) => odgovor.data)
}

export function kreirajAutomobil(dto) {
  return http.post('/api/automobili', dto).then((odgovor) => odgovor.data)
}

export function obrisiAutomobil(id) {
  return http.delete(`/api/automobili/${id}`)
}
