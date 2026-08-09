package com.example.autonaj2.controller;

import com.example.autonaj2.dto.AutomobilRequestDto;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.repository.FilijalaRepository;
import com.example.autonaj2.repository.KategorijaVozilaRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutomobilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KategorijaVozilaRepository kategorijaVozilaRepository;

    @Autowired
    private FilijalaRepository filijalaRepository;

    private Long kategorijaId;
    private Long filijalaId;

    @BeforeEach
    void pripremiFixture() {
        KategorijaVozila kategorija = new KategorijaVozila();
        kategorija.setNaziv("Ekonomska");
        kategorijaId = kategorijaVozilaRepository.save(kategorija).getId();

        Filijala filijala = new Filijala();
        filijala.setNaziv("AutoNaj Beograd");
        filijala.setGrad("Beograd");
        filijala.setAdresa("Bulevar 1");
        filijalaId = filijalaRepository.save(filijala).getId();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_povezujeKategorijuIFilijaluIVraca201() throws Exception {
        AutomobilRequestDto dto = new AutomobilRequestDto();
        dto.setMarka("Volkswagen");
        dto.setModel("Golf 7");
        dto.setGodiste(2019);
        dto.setRegistarskiBroj("BG-123-AA");
        dto.setCenaPoDanu(new BigDecimal("35.00"));
        dto.setKategorijaId(kategorijaId);
        dto.setFilijalaId(filijalaId);

        mockMvc.perform(post("/api/automobili")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.kategorijaNaziv").value("Ekonomska"))
                .andExpect(jsonPath("$.filijalaNaziv").value("AutoNaj Beograd"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_nepostojecaKategorijaVraca404() throws Exception {
        AutomobilRequestDto dto = new AutomobilRequestDto();
        dto.setMarka("Volkswagen");
        dto.setModel("Golf 7");
        dto.setGodiste(2019);
        dto.setRegistarskiBroj("BG-123-AA");
        dto.setCenaPoDanu(new BigDecimal("35.00"));
        dto.setKategorijaId(999L);
        dto.setFilijalaId(filijalaId);

        mockMvc.perform(post("/api/automobili")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void kreiraj_kaoObicanKorisnikVraca403() throws Exception {
        AutomobilRequestDto dto = new AutomobilRequestDto();
        dto.setMarka("Volkswagen");
        dto.setModel("Golf 7");
        dto.setGodiste(2019);
        dto.setRegistarskiBroj("BG-123-AA");
        dto.setCenaPoDanu(new BigDecimal("35.00"));
        dto.setKategorijaId(kategorijaId);
        dto.setFilijalaId(filijalaId);

        mockMvc.perform(post("/api/automobili")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}
