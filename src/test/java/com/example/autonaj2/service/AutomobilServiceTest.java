package com.example.autonaj2.service;

import com.example.autonaj2.dto.AutomobilRequestDto;
import com.example.autonaj2.entity.Automobil;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.repository.AutomobilRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutomobilServiceTest {

    @Mock
    private AutomobilRepository automobilRepository;

    @Mock
    private KategorijaVozilaService kategorijaVozilaService;

    @Mock
    private FilijalaService filijalaService;

    @InjectMocks
    private AutomobilService automobilService;

    @Test
    void kreiraj_bacaIzuzetakKadaRegistarskiBrojVecPostoji() {
        AutomobilRequestDto dto = new AutomobilRequestDto();
        dto.setRegistarskiBroj("BG-123-AA");
        when(automobilRepository.existsByRegistarskiBroj("BG-123-AA")).thenReturn(true);

        assertThrows(PoslovnoPraviloException.class, () -> automobilService.kreiraj(dto));
        verify(automobilRepository, never()).save(any());
    }

    @Test
    void kreiraj_bacaIzuzetakKadaKategorijaNePostoji() {
        AutomobilRequestDto dto = new AutomobilRequestDto();
        dto.setRegistarskiBroj("BG-999-ZZ");
        dto.setKategorijaId(42L);
        when(automobilRepository.existsByRegistarskiBroj("BG-999-ZZ")).thenReturn(false);
        when(kategorijaVozilaService.pronadjiPoId(42L))
                .thenThrow(new NijeNadjenoException("Kategorija sa id 42 ne postoji"));

        assertThrows(NijeNadjenoException.class, () -> automobilService.kreiraj(dto));
        verify(automobilRepository, never()).save(any());
    }

    @Test
    void kreiraj_povezujeKategorijuIFilijaluSaAutomobilom() {
        AutomobilRequestDto dto = new AutomobilRequestDto();
        dto.setMarka("Skoda");
        dto.setModel("Octavia");
        dto.setGodiste(2020);
        dto.setRegistarskiBroj("NS-111-DD");
        dto.setCenaPoDanu(new BigDecimal("40.00"));
        dto.setKategorijaId(1L);
        dto.setFilijalaId(2L);

        KategorijaVozila kategorija = new KategorijaVozila();
        kategorija.setId(1L);
        Filijala filijala = new Filijala();
        filijala.setId(2L);

        when(automobilRepository.existsByRegistarskiBroj("NS-111-DD")).thenReturn(false);
        when(kategorijaVozilaService.pronadjiPoId(1L)).thenReturn(kategorija);
        when(filijalaService.pronadjiPoId(2L)).thenReturn(filijala);
        when(automobilRepository.save(any(Automobil.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Automobil rezultat = automobilService.kreiraj(dto);

        assertEquals(kategorija, rezultat.getKategorija());
        assertEquals(filijala, rezultat.getFilijala());
        assertEquals("NS-111-DD", rezultat.getRegistarskiBroj());
    }
}
