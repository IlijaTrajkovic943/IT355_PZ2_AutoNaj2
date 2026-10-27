# AutoNaj 2 - backend

Spring Boot REST API za sistem za iznajmljivanje vozila (drugi projektni zadatak, IT355).

## Tehnologije

- Java 26, Spring Boot 4.1
- Spring Data JPA + H2 In-Memory Baza
- Spring Security 7 + JWT (jjwt)
- BCrypt heširanje lozinki
- JUnit 5, Mockito, MockMvc (H2 in-memory za testove)

## Pokretanje

1. Pokrenuti `Autonaj2Application` iz IDE-a ili preko komande `./mvnw spring-boot:run`.
2. Hibernate pri pokretanju automatski kreira sve tabele u H2 bazi u memoriji (`spring.jpa.hibernate.ddl-auto=update`), a `DataSeeder` ih automatski puni test podacima. Nije potrebna nikakva prethodna instalacija baze niti pokretanje eksternih servisa (XAMPP/MySQL).
3. API je dostupan na `http://localhost:8080`.

### H2 Web Konzola

Pregled baze u memoriji i kreiranih tabela dostupan je putem pretraživača dok je aplikacija pokrenuta:
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** *(prazno)*

Test nalozi koje `DataSeeder` kreira:

| Uloga | Email | Lozinka |
|---|---|---|
| Admin | admin@autonaj.rs | admin123 |
| Korisnik | marko@autonaj.rs | user123 |

## Testovi

```bash
./mvnw test
