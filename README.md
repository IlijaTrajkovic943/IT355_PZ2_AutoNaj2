# AutoNaj 2 - backend

Spring Boot REST API za sistem za iznajmljivanje vozila (drugi projektni zadatak, IT355).

## Tehnologije

- Java 26, Spring Boot 4.1
- Spring Data JPA + MySQL
- Spring Security 7 + JWT (jjwt)
- BCrypt heširanje lozinki
- JUnit 5, Mockito, MockMvc (H2 in-memory za testove)

## Pokretanje

1. Pokrenuti MySQL (npr. preko XAMPP-a).
2. U phpMyAdmin-u (ili drugim alatom) napraviti praznu bazu pod imenom `autonaj_db` - nije potrebno kreirati tabele ni uvoziti bilo kakav SQL dump, samo prazna baza sa tim imenom.
3. Pokrenuti `Autonaj2Application` iz IDE-a ili `./mvnw spring-boot:run`. Hibernate pri pokretanju sam kreira sve tabele (`spring.jpa.hibernate.ddl-auto=update`), a `DataSeeder` ih automatski puni test podacima.
4. API je dostupan na `http://localhost:8080`.

Konekcija na bazu (ime, korisnik, lozinka) je podešena u `src/main/resources/application.properties`.

Test nalozi koje `DataSeeder` kreira:

| Uloga | Email | Lozinka |
|---|---|---|
| Admin | admin@autonaj.rs | admin123 |
| Korisnik | marko@autonaj.rs | user123 |

## Testovi

```
./mvnw test
```

## Frontend

React aplikacija se nalazi u `frontend/` folderu - videti `frontend/README.md`.
