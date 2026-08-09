package com.example.autonaj2.service;

import com.example.autonaj2.dto.KorisnikRequestDto;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Uloga;
import com.example.autonaj2.repository.KorisnikRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KorisnikServiceTest {

    @Mock
    private KorisnikRepository korisnikRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private KorisnikService korisnikService;

    @Test
    void kreiraj_bacaIzuzetakKadaEmailVecPostoji() {
        KorisnikRequestDto dto = new KorisnikRequestDto();
        dto.setEmail("marko@autonaj.rs");
        when(korisnikRepository.existsByEmail("marko@autonaj.rs")).thenReturn(true);

        assertThrows(PoslovnoPraviloException.class, () -> korisnikService.kreiraj(dto));
        verify(korisnikRepository, never()).save(any());
    }

    @Test
    void kreiraj_hesujeLozinkuPreCuvanja() {
        KorisnikRequestDto dto = new KorisnikRequestDto();
        dto.setIme("Ana");
        dto.setPrezime("Anic");
        dto.setEmail("ana@autonaj.rs");
        dto.setLozinka("sifra123");
        dto.setUloga(Uloga.ROLE_USER);
        when(korisnikRepository.existsByEmail("ana@autonaj.rs")).thenReturn(false);
        when(passwordEncoder.encode("sifra123")).thenReturn("HESOVANA_LOZINKA");
        when(korisnikRepository.save(any(Korisnik.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Korisnik rezultat = korisnikService.kreiraj(dto);

        assertEquals("HESOVANA_LOZINKA", rezultat.getLozinka());
    }

    @Test
    void pronadjiPoId_bacaIzuzetakKadaNePostoji() {
        when(korisnikRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NijeNadjenoException.class, () -> korisnikService.pronadjiPoId(1L));
    }
}
