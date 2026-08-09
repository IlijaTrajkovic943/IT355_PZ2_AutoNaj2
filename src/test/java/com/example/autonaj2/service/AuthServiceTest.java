package com.example.autonaj2.service;

import com.example.autonaj2.dto.PrijavaOdgovorDto;
import com.example.autonaj2.dto.PrijavaRequestDto;
import com.example.autonaj2.dto.RegistracijaRequestDto;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Uloga;
import com.example.autonaj2.repository.KorisnikRepository;
import com.example.autonaj2.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private KorisnikRepository korisnikRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registruj_bacaIzuzetakKadaEmailVecPostoji() {
        RegistracijaRequestDto dto = new RegistracijaRequestDto();
        dto.setEmail("marko@autonaj.rs");
        when(korisnikRepository.existsByEmail("marko@autonaj.rs")).thenReturn(true);

        assertThrows(PoslovnoPraviloException.class, () -> authService.registruj(dto));
        verify(korisnikRepository, never()).save(any());
    }

    @Test
    void registruj_dodeljujeUlogaUserIVracaToken() {
        RegistracijaRequestDto dto = new RegistracijaRequestDto();
        dto.setIme("Ana");
        dto.setPrezime("Anic");
        dto.setEmail("ana@autonaj.rs");
        dto.setLozinka("sifra123");
        when(korisnikRepository.existsByEmail("ana@autonaj.rs")).thenReturn(false);
        when(passwordEncoder.encode("sifra123")).thenReturn("HES");
        when(jwtService.generisiToken("ana@autonaj.rs", "ROLE_USER")).thenReturn("token-123");

        PrijavaOdgovorDto rezultat = authService.registruj(dto);

        verify(korisnikRepository).save(any(Korisnik.class));
        assertEquals("token-123", rezultat.getToken());
        assertEquals("ROLE_USER", rezultat.getUloga());
    }

    @Test
    void prijavi_bacaIzuzetakKadaSuKredencijaliPogresni() {
        PrijavaRequestDto dto = new PrijavaRequestDto();
        dto.setEmail("marko@autonaj.rs");
        dto.setLozinka("pogresna");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Los login"));

        assertThrows(BadCredentialsException.class, () -> authService.prijavi(dto));
    }

    @Test
    void prijavi_vracaTokenKadaSuKredencijaliIspravni() {
        PrijavaRequestDto dto = new PrijavaRequestDto();
        dto.setEmail("marko@autonaj.rs");
        dto.setLozinka("user123");

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("marko@autonaj.rs");
        korisnik.setUloga(Uloga.ROLE_USER);
        when(korisnikRepository.findByEmail("marko@autonaj.rs")).thenReturn(Optional.of(korisnik));
        when(jwtService.generisiToken("marko@autonaj.rs", "ROLE_USER")).thenReturn("token-456");

        PrijavaOdgovorDto rezultat = authService.prijavi(dto);

        assertEquals("token-456", rezultat.getToken());
    }
}
