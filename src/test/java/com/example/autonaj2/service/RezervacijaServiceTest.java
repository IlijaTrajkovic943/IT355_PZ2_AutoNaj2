package com.example.autonaj2.service;

import com.example.autonaj2.dto.RezervacijaRequestDto;
import com.example.autonaj2.entity.Automobil;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Rezervacija;
import com.example.autonaj2.entity.StatusRezervacije;
import com.example.autonaj2.repository.RezervacijaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RezervacijaServiceTest {

    @Mock
    private RezervacijaRepository rezervacijaRepository;

    @Mock
    private KorisnikService korisnikService;

    @Mock
    private AutomobilService automobilService;

    @InjectMocks
    private RezervacijaService rezervacijaService;

    @Test
    void kreiraj_bacaIzuzetakKadaDatumDoNijePosleDatumaOd() {
        RezervacijaRequestDto dto = new RezervacijaRequestDto();
        dto.setKorisnikId(1L);
        dto.setAutomobilId(1L);
        dto.setDatumOd(LocalDate.of(2026, 9, 10));
        dto.setDatumDo(LocalDate.of(2026, 9, 10));

        assertThrows(PoslovnoPraviloException.class, () -> rezervacijaService.kreiraj(dto));
        verify(rezervacijaRepository, never()).save(any());
    }

    @Test
    void kreiraj_bacaIzuzetakKadaSePreklapaSaPostojecomRezervacijom() {
        RezervacijaRequestDto dto = new RezervacijaRequestDto();
        dto.setKorisnikId(1L);
        dto.setAutomobilId(5L);
        dto.setDatumOd(LocalDate.of(2026, 9, 1));
        dto.setDatumDo(LocalDate.of(2026, 9, 5));

        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);
        Automobil automobil = new Automobil();
        automobil.setId(5L);
        automobil.setCenaPoDanu(new BigDecimal("30.00"));

        when(korisnikService.pronadjiPoId(1L)).thenReturn(korisnik);
        when(automobilService.pronadjiPoId(5L)).thenReturn(automobil);
        when(rezervacijaRepository.pronadjiPreklapajuce(5L, dto.getDatumOd(), dto.getDatumDo()))
                .thenReturn(List.of(new Rezervacija()));

        assertThrows(PoslovnoPraviloException.class, () -> rezervacijaService.kreiraj(dto));
        verify(rezervacijaRepository, never()).save(any());
    }

    @Test
    void kreiraj_racunaUkupnuCenuIPostavljaStatusNaCekanju() {
        RezervacijaRequestDto dto = new RezervacijaRequestDto();
        dto.setKorisnikId(1L);
        dto.setAutomobilId(5L);
        dto.setDatumOd(LocalDate.of(2026, 9, 1));
        dto.setDatumDo(LocalDate.of(2026, 9, 4));

        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);
        Automobil automobil = new Automobil();
        automobil.setId(5L);
        automobil.setMarka("Golf");
        automobil.setCenaPoDanu(new BigDecimal("30.00"));

        when(korisnikService.pronadjiPoId(1L)).thenReturn(korisnik);
        when(automobilService.pronadjiPoId(5L)).thenReturn(automobil);
        when(rezervacijaRepository.pronadjiPreklapajuce(anyLong(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(rezervacijaRepository.save(any(Rezervacija.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rezervacija rezultat = rezervacijaService.kreiraj(dto);

        // 3 dana (1.9 - 4.9) * 30.00 = 90.00
        assertEquals(0, new BigDecimal("90.00").compareTo(rezultat.getUkupnaCena()));
        assertEquals(StatusRezervacije.NA_CEKANJU, rezultat.getStatus());
    }

    @Test
    void otkazi_postavljaStatusOtkazana() {
        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setId(7L);
        rezervacija.setStatus(StatusRezervacije.POTVRDJENA);
        when(rezervacijaRepository.findById(7L)).thenReturn(Optional.of(rezervacija));
        when(rezervacijaRepository.save(any(Rezervacija.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rezervacija rezultat = rezervacijaService.otkazi(7L);

        assertEquals(StatusRezervacije.OTKAZANA, rezultat.getStatus());
    }

    @Test
    void potvrdi_postavljaStatusPotvrdjena() {
        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setId(8L);
        rezervacija.setStatus(StatusRezervacije.NA_CEKANJU);
        when(rezervacijaRepository.findById(8L)).thenReturn(Optional.of(rezervacija));
        when(rezervacijaRepository.save(any(Rezervacija.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rezervacija rezultat = rezervacijaService.potvrdi(8L);

        assertEquals(StatusRezervacije.POTVRDJENA, rezultat.getStatus());
    }
}
