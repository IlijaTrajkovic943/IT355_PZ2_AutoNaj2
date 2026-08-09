package com.example.autonaj2.service;

import com.example.autonaj2.dto.KategorijaVozilaRequestDto;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.repository.KategorijaVozilaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KategorijaVozilaServiceTest {

    @Mock
    private KategorijaVozilaRepository kategorijaVozilaRepository;

    @InjectMocks
    private KategorijaVozilaService kategorijaVozilaService;

    @Test
    void kreiraj_bacaIzuzetakKadaNazivVecPostoji() {
        KategorijaVozilaRequestDto dto = new KategorijaVozilaRequestDto();
        dto.setNaziv("Ekonomska");
        when(kategorijaVozilaRepository.existsByNaziv("Ekonomska")).thenReturn(true);

        assertThrows(PoslovnoPraviloException.class, () -> kategorijaVozilaService.kreiraj(dto));
        verify(kategorijaVozilaRepository, never()).save(any());
    }

    @Test
    void kreiraj_cuvaKategorijuKadaNazivNijeZauzet() {
        KategorijaVozilaRequestDto dto = new KategorijaVozilaRequestDto();
        dto.setNaziv("SUV");
        dto.setOpis("Terenska vozila");
        when(kategorijaVozilaRepository.existsByNaziv("SUV")).thenReturn(false);
        when(kategorijaVozilaRepository.save(any(KategorijaVozila.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        KategorijaVozila rezultat = kategorijaVozilaService.kreiraj(dto);

        verify(kategorijaVozilaRepository).save(any(KategorijaVozila.class));
        assertEquals("SUV", rezultat.getNaziv());
    }
}
