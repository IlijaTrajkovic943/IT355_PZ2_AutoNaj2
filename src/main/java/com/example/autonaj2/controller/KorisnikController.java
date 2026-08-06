package com.example.autonaj2.controller;

import com.example.autonaj2.dto.KorisnikDto;
import com.example.autonaj2.dto.KorisnikRequestDto;
import com.example.autonaj2.service.KorisnikService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @GetMapping
    public List<KorisnikDto> sviKorisnici() {
        return korisnikService.pronadjiSve().stream().map(KorisnikDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public KorisnikDto jedanKorisnik(@PathVariable Long id) {
        return KorisnikDto.fromEntity(korisnikService.pronadjiPoId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KorisnikDto kreirajKorisnika(@Valid @RequestBody KorisnikRequestDto dto) {
        return KorisnikDto.fromEntity(korisnikService.kreiraj(dto));
    }

    @PutMapping("/{id}")
    public KorisnikDto izmeniKorisnika(@PathVariable Long id, @Valid @RequestBody KorisnikRequestDto dto) {
        return KorisnikDto.fromEntity(korisnikService.izmeni(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiKorisnika(@PathVariable Long id) {
        korisnikService.obrisi(id);
    }
}
