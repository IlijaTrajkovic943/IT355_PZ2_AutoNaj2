package com.example.autonaj2.service;

import com.example.autonaj2.dto.PrijavaOdgovorDto;
import com.example.autonaj2.dto.PrijavaRequestDto;
import com.example.autonaj2.dto.RegistracijaRequestDto;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Uloga;
import com.example.autonaj2.repository.KorisnikRepository;
import com.example.autonaj2.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(KorisnikRepository korisnikRepository, PasswordEncoder passwordEncoder,
                        JwtService jwtService, AuthenticationManager authenticationManager) {
        this.korisnikRepository = korisnikRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public PrijavaOdgovorDto registruj(RegistracijaRequestDto dto) {
        if (korisnikRepository.existsByEmail(dto.getEmail())) {
            throw new PoslovnoPraviloException("Korisnik sa email adresom '" + dto.getEmail() + "' vec postoji");
        }
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(dto.getIme());
        korisnik.setPrezime(dto.getPrezime());
        korisnik.setEmail(dto.getEmail());
        korisnik.setLozinka(passwordEncoder.encode(dto.getLozinka()));
        korisnik.setUloga(Uloga.ROLE_USER);
        korisnikRepository.save(korisnik);

        String token = jwtService.generisiToken(korisnik.getEmail(), korisnik.getUloga().name());
        return new PrijavaOdgovorDto(korisnik.getId(), token, korisnik.getEmail(), korisnik.getUloga().name());
    }

    public PrijavaOdgovorDto prijavi(PrijavaRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getLozinka()));

        Korisnik korisnik = korisnikRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NijeNadjenoException("Korisnik ne postoji"));

        String token = jwtService.generisiToken(korisnik.getEmail(), korisnik.getUloga().name());
        return new PrijavaOdgovorDto(korisnik.getId(), token, korisnik.getEmail(), korisnik.getUloga().name());
    }
}
