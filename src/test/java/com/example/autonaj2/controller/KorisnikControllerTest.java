package com.example.autonaj2.controller;

import com.example.autonaj2.dto.KorisnikRequestDto;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Uloga;
import com.example.autonaj2.repository.KorisnikRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class KorisnikControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(roles = "USER")
    void getSve_dozvoljenoIObicnomKorisniku() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Marko");
        korisnik.setPrezime("Markovic");
        korisnik.setEmail("marko@autonaj.rs");
        korisnik.setLozinka(passwordEncoder.encode("user123"));
        korisnik.setUloga(Uloga.ROLE_USER);
        korisnikRepository.save(korisnik);

        mockMvc.perform(get("/api/korisnici"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("marko@autonaj.rs"))
                .andExpect(jsonPath("$[0].lozinka").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_duplikatEmailaVraca409() throws Exception {
        Korisnik postojeci = new Korisnik();
        postojeci.setIme("Marko");
        postojeci.setPrezime("Markovic");
        postojeci.setEmail("marko@autonaj.rs");
        postojeci.setLozinka(passwordEncoder.encode("user123"));
        postojeci.setUloga(Uloga.ROLE_USER);
        korisnikRepository.save(postojeci);

        KorisnikRequestDto dto = new KorisnikRequestDto();
        dto.setIme("Drugi");
        dto.setPrezime("Korisnik");
        dto.setEmail("marko@autonaj.rs");
        dto.setLozinka("sifra123");
        dto.setUloga(Uloga.ROLE_USER);

        mockMvc.perform(post("/api/korisnici")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "USER")
    void kreiraj_kaoObicanKorisnikVraca403() throws Exception {
        KorisnikRequestDto dto = new KorisnikRequestDto();
        dto.setIme("Ana");
        dto.setPrezime("Anic");
        dto.setEmail("ana@autonaj.rs");
        dto.setLozinka("sifra123");
        dto.setUloga(Uloga.ROLE_USER);

        mockMvc.perform(post("/api/korisnici")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}
