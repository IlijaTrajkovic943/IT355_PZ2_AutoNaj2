package com.example.autonaj2.controller;

import com.example.autonaj2.dto.KategorijaVozilaRequestDto;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.repository.KategorijaVozilaRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class KategorijaVozilaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KategorijaVozilaRepository kategorijaVozilaRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_kaoAdminVraca201() throws Exception {
        KategorijaVozilaRequestDto dto = new KategorijaVozilaRequestDto();
        dto.setNaziv("Premium");
        dto.setOpis("Luksuzna vozila");

        mockMvc.perform(post("/api/kategorije")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.naziv").value("Premium"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void kreiraj_duplikatNazivaVraca409() throws Exception {
        KategorijaVozila postojeca = new KategorijaVozila();
        postojeca.setNaziv("Ekonomska");
        kategorijaVozilaRepository.save(postojeca);

        KategorijaVozilaRequestDto dto = new KategorijaVozilaRequestDto();
        dto.setNaziv("Ekonomska");

        mockMvc.perform(post("/api/kategorije")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "USER")
    void kreiraj_kaoObicanKorisnikVraca403() throws Exception {
        KategorijaVozilaRequestDto dto = new KategorijaVozilaRequestDto();
        dto.setNaziv("Premium");

        mockMvc.perform(post("/api/kategorije")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}
