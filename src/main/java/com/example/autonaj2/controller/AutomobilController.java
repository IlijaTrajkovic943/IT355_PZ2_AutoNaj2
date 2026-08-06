package com.example.autonaj2.controller;

import com.example.autonaj2.dto.AutomobilDto;
import com.example.autonaj2.dto.AutomobilRequestDto;
import com.example.autonaj2.service.AutomobilService;
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
@RequestMapping("/api/automobili")
public class AutomobilController {

    private final AutomobilService automobilService;

    public AutomobilController(AutomobilService automobilService) {
        this.automobilService = automobilService;
    }

    @GetMapping
    public List<AutomobilDto> sviAutomobili() {
        return automobilService.pronadjiSve().stream().map(AutomobilDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public AutomobilDto jedanAutomobil(@PathVariable Long id) {
        return AutomobilDto.fromEntity(automobilService.pronadjiPoId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutomobilDto kreirajAutomobil(@Valid @RequestBody AutomobilRequestDto dto) {
        return AutomobilDto.fromEntity(automobilService.kreiraj(dto));
    }

    @PutMapping("/{id}")
    public AutomobilDto izmeniAutomobil(@PathVariable Long id, @Valid @RequestBody AutomobilRequestDto dto) {
        return AutomobilDto.fromEntity(automobilService.izmeni(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiAutomobil(@PathVariable Long id) {
        automobilService.obrisi(id);
    }
}
