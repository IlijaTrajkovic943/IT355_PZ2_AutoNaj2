package com.example.autonaj2.controller;

import com.example.autonaj2.dto.RezervacijaDto;
import com.example.autonaj2.dto.RezervacijaRequestDto;
import com.example.autonaj2.service.RezervacijaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rezervacije")
public class RezervacijaController {

    private final RezervacijaService rezervacijaService;

    public RezervacijaController(RezervacijaService rezervacijaService) {
        this.rezervacijaService = rezervacijaService;
    }

    @GetMapping
    public List<RezervacijaDto> sveRezervacije() {
        return rezervacijaService.pronadjiSve().stream().map(RezervacijaDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public RezervacijaDto jednaRezervacija(@PathVariable Long id) {
        return RezervacijaDto.fromEntity(rezervacijaService.pronadjiPoId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RezervacijaDto kreirajRezervaciju(@Valid @RequestBody RezervacijaRequestDto dto) {
        return RezervacijaDto.fromEntity(rezervacijaService.kreiraj(dto));
    }

    @PatchMapping("/{id}/otkazi")
    public RezervacijaDto otkaziRezervaciju(@PathVariable Long id) {
        return RezervacijaDto.fromEntity(rezervacijaService.otkazi(id));
    }

    @PatchMapping("/{id}/potvrdi")
    public RezervacijaDto potvrdiRezervaciju(@PathVariable Long id) {
        return RezervacijaDto.fromEntity(rezervacijaService.potvrdi(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiRezervaciju(@PathVariable Long id) {
        rezervacijaService.obrisi(id);
    }
}
