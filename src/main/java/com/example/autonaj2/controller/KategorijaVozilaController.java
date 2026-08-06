package com.example.autonaj2.controller;

import com.example.autonaj2.dto.KategorijaVozilaDto;
import com.example.autonaj2.dto.KategorijaVozilaRequestDto;
import com.example.autonaj2.service.KategorijaVozilaService;
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
@RequestMapping("/api/kategorije")
public class KategorijaVozilaController {

    private final KategorijaVozilaService kategorijaVozilaService;

    public KategorijaVozilaController(KategorijaVozilaService kategorijaVozilaService) {
        this.kategorijaVozilaService = kategorijaVozilaService;
    }

    @GetMapping
    public List<KategorijaVozilaDto> sveKategorije() {
        return kategorijaVozilaService.pronadjiSve().stream().map(KategorijaVozilaDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public KategorijaVozilaDto jednaKategorija(@PathVariable Long id) {
        return KategorijaVozilaDto.fromEntity(kategorijaVozilaService.pronadjiPoId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KategorijaVozilaDto kreirajKategoriju(@Valid @RequestBody KategorijaVozilaRequestDto dto) {
        return KategorijaVozilaDto.fromEntity(kategorijaVozilaService.kreiraj(dto));
    }

    @PutMapping("/{id}")
    public KategorijaVozilaDto izmeniKategoriju(@PathVariable Long id,
                                                 @Valid @RequestBody KategorijaVozilaRequestDto dto) {
        return KategorijaVozilaDto.fromEntity(kategorijaVozilaService.izmeni(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiKategoriju(@PathVariable Long id) {
        kategorijaVozilaService.obrisi(id);
    }
}
