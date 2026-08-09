package com.example.autonaj2.controller;

import com.example.autonaj2.dto.RezervacijaRequestDto;
import com.example.autonaj2.entity.Automobil;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Rezervacija;
import com.example.autonaj2.entity.StatusRezervacije;
import com.example.autonaj2.entity.Uloga;
import com.example.autonaj2.repository.AutomobilRepository;
import com.example.autonaj2.repository.FilijalaRepository;
import com.example.autonaj2.repository.KategorijaVozilaRepository;
import com.example.autonaj2.repository.KorisnikRepository;
import com.example.autonaj2.repository.RezervacijaRepository;
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
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RezervacijaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private AutomobilRepository automobilRepository;

    @Autowired
    private KategorijaVozilaRepository kategorijaVozilaRepository;

    @Autowired
    private FilijalaRepository filijalaRepository;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    private Long korisnikId;
    private Long automobilId;

    @BeforeEach
    void pripremiFixture() {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Marko");
        korisnik.setPrezime("Markovic");
        korisnik.setEmail("marko@autonaj.rs");
        korisnik.setLozinka("nebitno-za-ovaj-test");
        korisnik.setUloga(Uloga.ROLE_USER);
        korisnikId = korisnikRepository.save(korisnik).getId();

        KategorijaVozila kategorija = new KategorijaVozila();
        kategorija.setNaziv("Ekonomska");
        kategorija = kategorijaVozilaRepository.save(kategorija);

        Filijala filijala = new Filijala();
        filijala.setNaziv("AutoNaj Beograd");
        filijala.setGrad("Beograd");
        filijala.setAdresa("Bulevar 1");
        filijala = filijalaRepository.save(filijala);

        Automobil automobil = new Automobil();
        automobil.setMarka("Volkswagen");
        automobil.setModel("Golf 7");
        automobil.setGodiste(2019);
        automobil.setRegistarskiBroj("BG-123-AA");
        automobil.setCenaPoDanu(new BigDecimal("30.00"));
        automobil.setDostupan(true);
        automobil.setKategorija(kategorija);
        automobil.setFilijala(filijala);
        automobilId = automobilRepository.save(automobil).getId();
    }

    @Test
    @WithMockUser(roles = "USER")
    void kreiraj_racunaUkupnuCenuIVraca201() throws Exception {
        RezervacijaRequestDto dto = new RezervacijaRequestDto();
        dto.setKorisnikId(korisnikId);
        dto.setAutomobilId(automobilId);
        dto.setDatumOd(LocalDate.now().plusDays(1));
        dto.setDatumDo(LocalDate.now().plusDays(4));

        // 3 dana * 30.00 = 90.00
        mockMvc.perform(post("/api/rezervacije")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ukupnaCena").value(90.0))
                .andExpect(jsonPath("$.status").value("NA_CEKANJU"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void kreiraj_preklapajucTerminVraca409() throws Exception {
        Rezervacija postojeca = new Rezervacija();
        postojeca.setKorisnik(korisnikRepository.findById(korisnikId).orElseThrow());
        postojeca.setAutomobil(automobilRepository.findById(automobilId).orElseThrow());
        postojeca.setDatumOd(LocalDate.now().plusDays(1));
        postojeca.setDatumDo(LocalDate.now().plusDays(5));
        postojeca.setUkupnaCena(new BigDecimal("120.00"));
        postojeca.setStatus(StatusRezervacije.POTVRDJENA);
        rezervacijaRepository.save(postojeca);

        RezervacijaRequestDto dto = new RezervacijaRequestDto();
        dto.setKorisnikId(korisnikId);
        dto.setAutomobilId(automobilId);
        dto.setDatumOd(LocalDate.now().plusDays(3));
        dto.setDatumDo(LocalDate.now().plusDays(6));

        mockMvc.perform(post("/api/rezervacije")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "USER")
    void otkazi_postavljaStatusOtkazana() throws Exception {
        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnikRepository.findById(korisnikId).orElseThrow());
        rezervacija.setAutomobil(automobilRepository.findById(automobilId).orElseThrow());
        rezervacija.setDatumOd(LocalDate.now().plusDays(1));
        rezervacija.setDatumDo(LocalDate.now().plusDays(2));
        rezervacija.setUkupnaCena(new BigDecimal("30.00"));
        rezervacija.setStatus(StatusRezervacije.NA_CEKANJU);
        Long id = rezervacijaRepository.save(rezervacija).getId();

        mockMvc.perform(patch("/api/rezervacije/" + id + "/otkazi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OTKAZANA"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void obrisi_dozvoljenoIObicnomKorisniku() throws Exception {
        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnikRepository.findById(korisnikId).orElseThrow());
        rezervacija.setAutomobil(automobilRepository.findById(automobilId).orElseThrow());
        rezervacija.setDatumOd(LocalDate.now().plusDays(1));
        rezervacija.setDatumDo(LocalDate.now().plusDays(2));
        rezervacija.setUkupnaCena(new BigDecimal("30.00"));
        rezervacija.setStatus(StatusRezervacije.NA_CEKANJU);
        Long id = rezervacijaRepository.save(rezervacija).getId();

        mockMvc.perform(delete("/api/rezervacije/" + id))
                .andExpect(status().isNoContent());
    }
}
