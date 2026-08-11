# AutoNaj 2 - frontend

React (Vite) aplikacija za sistem za iznajmljivanje vozila (drugi projektni zadatak, IT355). Komunicira sa backend REST API-jem iz korenskog foldera projekta.

## Tehnologije

- React 19 + Vite
- react-router-dom
- axios (JWT token se automatski šalje kroz `Authorization` header)

## Pokretanje

1. Prvo pokrenuti backend (videti `../README.md`) na `http://localhost:8080`.
2. Instalirati zavisnosti: `npm install`
3. Pokrenuti dev server: `npm run dev`
4. Otvoriti `http://localhost:5173`.

## Stranice

- `/prijava`, `/registracija` - prijava i registracija korisnika
- `/` - početna strana
- `/automobili` - pregled vozila i kreiranje rezervacije
- `/admin` - upravljanje filijalama, kategorijama i automobilima (samo ADMIN uloga)
