package com.example.autonaj2.controller;

import com.example.autonaj2.dto.FilijalaDto;
import com.example.autonaj2.dto.FilijalaRequestDto;
import com.example.autonaj2.service.FilijalaService;
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
@RequestMapping("/api/filijale")
public class FilijalaController {

    private final FilijalaService filijalaService;

    public FilijalaController(FilijalaService filijalaService) {
        this.filijalaService = filijalaService;
    }

    @GetMapping
    public List<FilijalaDto> sveFilijale() {
        return filijalaService.pronadjiSve().stream().map(FilijalaDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public FilijalaDto jednaFilijala(@PathVariable Long id) {
        return FilijalaDto.fromEntity(filijalaService.pronadjiPoId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilijalaDto kreirajFilijalu(@Valid @RequestBody FilijalaRequestDto dto) {
        return FilijalaDto.fromEntity(filijalaService.kreiraj(dto));
    }

    @PutMapping("/{id}")
    public FilijalaDto izmeniFilijalu(@PathVariable Long id, @Valid @RequestBody FilijalaRequestDto dto) {
        return FilijalaDto.fromEntity(filijalaService.izmeni(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiFilijalu(@PathVariable Long id) {
        filijalaService.obrisi(id);
    }
}
