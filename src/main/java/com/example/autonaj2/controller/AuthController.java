package com.example.autonaj2.controller;

import com.example.autonaj2.dto.PrijavaOdgovorDto;
import com.example.autonaj2.dto.PrijavaRequestDto;
import com.example.autonaj2.dto.RegistracijaRequestDto;
import com.example.autonaj2.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registracija")
    @ResponseStatus(HttpStatus.CREATED)
    public PrijavaOdgovorDto registracija(@Valid @RequestBody RegistracijaRequestDto dto) {
        return authService.registruj(dto);
    }

    @PostMapping("/prijava")
    public PrijavaOdgovorDto prijava(@Valid @RequestBody PrijavaRequestDto dto) {
        return authService.prijavi(dto);
    }
}
