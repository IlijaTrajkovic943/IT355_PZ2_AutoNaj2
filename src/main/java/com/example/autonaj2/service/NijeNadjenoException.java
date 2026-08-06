package com.example.autonaj2.service;

// baca se kada trazeni entitet (po id-u) ne postoji u bazi
public class NijeNadjenoException extends RuntimeException {

    public NijeNadjenoException(String poruka) {
        super(poruka);
    }
}
