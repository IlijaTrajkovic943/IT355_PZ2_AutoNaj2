package com.example.autonaj2.controller;

import com.example.autonaj2.dto.GreskaOdgovor;
import com.example.autonaj2.service.NijeNadjenoException;
import com.example.autonaj2.service.PoslovnoPraviloException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// hvata izuzetke iz svih kontrolera na jednom mestu, umesto da svaki kontroler
// pojedinacno hvata try/catch - Spring automatski prepozna koji handler odgovara bacenom izuzetku
@RestControllerAdvice
public class GlobalniExceptionHandler {

    @ExceptionHandler(NijeNadjenoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GreskaOdgovor obradiNijeNadjeno(NijeNadjenoException ex) {
        return new GreskaOdgovor(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(PoslovnoPraviloException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public GreskaOdgovor obradiPoslovnoPravilo(PoslovnoPraviloException ex) {
        return new GreskaOdgovor(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GreskaOdgovor obradiPogresnePodatke(BadCredentialsException ex) {
        return new GreskaOdgovor(HttpStatus.UNAUTHORIZED.value(), "Pogresan email ili lozinka");
    }

    // aktivira se kada @Valid na @RequestBody DTO-u otkrije neispravne podatke
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> obradiValidaciju(MethodArgumentNotValidException ex) {
        Map<String, String> greske = new HashMap<>();
        for (var fieldError : ex.getBindingResult().getFieldErrors()) {
            greske.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return greske;
    }
}
