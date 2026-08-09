package com.example.autonaj2.controller;

import com.example.autonaj2.dto.FilijalaRequestDto;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.repository.FilijalaRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest podize ceo Spring kontekst (kao prava aplikacija), @AutoConfigureMockMvc dodaje MockMvc
// koji simulira HTTP zahteve bez pokretanja pravog servera; @ActiveProfiles("test") koristi H2 iz application-test.properties
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FilijalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilijalaRepository filijalaRepository;

    @Test
    @WithMockUser(roles = "USER")
    void getSve_vracaListuKadaJeKorisnikPrijavljen() throws Exception {
        Filijala filijala = new Filijala();
        filijala.setNaziv("AutoNaj Beograd");
        filijala.setGrad("Beograd");
        filijala.setAdresa("Bulevar 1");
        filijalaRepository.save(filijala);

        mockMvc.perform(get("/api/filijale"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].naziv").value("AutoNaj Beograd"));
    }

    @Test
    void getSve_bezPrijaveVraca403() throws Exception {
        mockMvc.perform(get("/api/filijale"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_kaoAdminVraca201() throws Exception {
        FilijalaRequestDto dto = new FilijalaRequestDto();
        dto.setNaziv("AutoNaj Nis");
        dto.setGrad("Nis");
        dto.setAdresa("Nisavska 1");

        mockMvc.perform(post("/api/filijale")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.naziv").value("AutoNaj Nis"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void kreiraj_kaoObicanKorisnikVraca403() throws Exception {
        FilijalaRequestDto dto = new FilijalaRequestDto();
        dto.setNaziv("AutoNaj Nis");
        dto.setGrad("Nis");
        dto.setAdresa("Nisavska 1");

        mockMvc.perform(post("/api/filijale")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_nevalidanZahtevVraca400() throws Exception {
        FilijalaRequestDto dto = new FilijalaRequestDto();
        dto.setNaziv("");
        dto.setGrad("Nis");
        dto.setAdresa("Nisavska 1");

        mockMvc.perform(post("/api/filijale")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.naziv").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void obrisi_nepostojecaFilijalaVraca404() throws Exception {
        mockMvc.perform(delete("/api/filijale/999"))
                .andExpect(status().isNotFound());
    }
}
