package com.example.autonaj2.controller;

import com.example.autonaj2.dto.PrijavaRequestDto;
import com.example.autonaj2.dto.RegistracijaRequestDto;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// ovi endpointi su javni (permitAll), pa se ne koristi @WithMockUser - test ide kroz pravi registracija/login tok
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registracija_uspesnaVracaTokenIUlogu() throws Exception {
        RegistracijaRequestDto dto = new RegistracijaRequestDto();
        dto.setIme("Ana");
        dto.setPrezime("Anic");
        dto.setEmail("ana@autonaj.rs");
        dto.setLozinka("sifra123");

        mockMvc.perform(post("/auth/registracija")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.uloga").value("ROLE_USER"));
    }

    @Test
    void registracija_nevalidanEmailVraca400() throws Exception {
        RegistracijaRequestDto dto = new RegistracijaRequestDto();
        dto.setIme("Ana");
        dto.setPrezime("Anic");
        dto.setEmail("nije-email");
        dto.setLozinka("sifra123");

        mockMvc.perform(post("/auth/registracija")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registracija_duplikatEmailaVraca409() throws Exception {
        RegistracijaRequestDto dto = new RegistracijaRequestDto();
        dto.setIme("Ana");
        dto.setPrezime("Anic");
        dto.setEmail("ana@autonaj.rs");
        dto.setLozinka("sifra123");

        mockMvc.perform(post("/auth/registracija")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/auth/registracija")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void prijava_ispravniPodaciVracajuToken() throws Exception {
        RegistracijaRequestDto registracija = new RegistracijaRequestDto();
        registracija.setIme("Ana");
        registracija.setPrezime("Anic");
        registracija.setEmail("ana@autonaj.rs");
        registracija.setLozinka("sifra123");
        mockMvc.perform(post("/auth/registracija")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registracija)))
                .andExpect(status().isCreated());

        PrijavaRequestDto prijava = new PrijavaRequestDto();
        prijava.setEmail("ana@autonaj.rs");
        prijava.setLozinka("sifra123");

        mockMvc.perform(post("/auth/prijava")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(prijava)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void prijava_pogresnaLozinkaVraca401() throws Exception {
        RegistracijaRequestDto registracija = new RegistracijaRequestDto();
        registracija.setIme("Ana");
        registracija.setPrezime("Anic");
        registracija.setEmail("ana@autonaj.rs");
        registracija.setLozinka("sifra123");
        mockMvc.perform(post("/auth/registracija")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registracija)))
                .andExpect(status().isCreated());

        PrijavaRequestDto prijava = new PrijavaRequestDto();
        prijava.setEmail("ana@autonaj.rs");
        prijava.setLozinka("pogresna-lozinka");

        mockMvc.perform(post("/auth/prijava")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(prijava)))
                .andExpect(status().isUnauthorized());
    }
}
