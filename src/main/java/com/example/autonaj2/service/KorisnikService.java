package com.example.autonaj2.service;

import com.example.autonaj2.dto.KorisnikRequestDto;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.repository.KorisnikRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public KorisnikService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    public List<Korisnik> pronadjiSve() {
        return korisnikRepository.findAll();
    }

    public Korisnik pronadjiPoId(Long id) {
        return korisnikRepository.findById(id)
                .orElseThrow(() -> new NijeNadjenoException("Korisnik sa id " + id + " ne postoji"));
    }

    @Transactional
    public Korisnik kreiraj(KorisnikRequestDto dto) {
        if (korisnikRepository.existsByEmail(dto.getEmail())) {
            throw new PoslovnoPraviloException("Korisnik sa email adresom '" + dto.getEmail() + "' vec postoji");
        }
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(dto.getIme());
        korisnik.setPrezime(dto.getPrezime());
        korisnik.setEmail(dto.getEmail());
        // hesiranje lozinke (BCrypt) dolazi u F7 kad podesimo Spring Security
        korisnik.setLozinka(dto.getLozinka());
        korisnik.setUloga(dto.getUloga());
        return korisnikRepository.save(korisnik);
    }

    @Transactional
    public Korisnik izmeni(Long id, KorisnikRequestDto dto) {
        Korisnik korisnik = pronadjiPoId(id);
        korisnik.setIme(dto.getIme());
        korisnik.setPrezime(dto.getPrezime());
        korisnik.setEmail(dto.getEmail());
        korisnik.setUloga(dto.getUloga());
        return korisnikRepository.save(korisnik);
    }

    @Transactional
    public void obrisi(Long id) {
        Korisnik korisnik = pronadjiPoId(id);
        korisnikRepository.delete(korisnik);
    }
}
