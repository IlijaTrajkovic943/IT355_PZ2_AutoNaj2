package com.example.autonaj2.service;

import com.example.autonaj2.dto.FilijalaRequestDto;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.repository.FilijalaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class) - ukljucuje Mockito u JUnit 5 test, obradjuje @Mock i @InjectMocks anotacije
@ExtendWith(MockitoExtension.class)
class FilijalaServiceTest {

    @Mock
    private FilijalaRepository filijalaRepository;

    @InjectMocks
    private FilijalaService filijalaService;

    @Test
    void pronadjiPoId_vracaFilijaluKadaPostoji() {
        Filijala filijala = new Filijala();
        filijala.setId(1L);
        filijala.setNaziv("AutoNaj Beograd");
        when(filijalaRepository.findById(1L)).thenReturn(Optional.of(filijala));

        Filijala rezultat = filijalaService.pronadjiPoId(1L);

        assertEquals("AutoNaj Beograd", rezultat.getNaziv());
    }

    @Test
    void pronadjiPoId_bacaIzuzetakKadaNePostoji() {
        when(filijalaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NijeNadjenoException.class, () -> filijalaService.pronadjiPoId(99L));
    }

    @Test
    void kreiraj_cuvaFilijaluSaPodacimaIzDto() {
        FilijalaRequestDto dto = new FilijalaRequestDto();
        dto.setNaziv("AutoNaj Nis");
        dto.setGrad("Nis");
        dto.setAdresa("Nisavska 1");
        when(filijalaRepository.save(any(Filijala.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Filijala rezultat = filijalaService.kreiraj(dto);

        assertEquals("AutoNaj Nis", rezultat.getNaziv());
        assertEquals("Nis", rezultat.getGrad());
        assertEquals("Nisavska 1", rezultat.getAdresa());
    }

    @Test
    void obrisi_bacaIzuzetakKadaFilijalaNePostojiIDeleteSeNeIzvrsava() {
        when(filijalaRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(NijeNadjenoException.class, () -> filijalaService.obrisi(5L));
        verify(filijalaRepository, never()).delete(any());
    }
}
