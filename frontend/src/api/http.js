import axios from 'axios'

const http = axios.create({
  baseURL: 'http://localhost:8080',
})

// pre svakog zahteva ubacuje JWT token (ako postoji u localStorage) u Authorization header
http.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default http
