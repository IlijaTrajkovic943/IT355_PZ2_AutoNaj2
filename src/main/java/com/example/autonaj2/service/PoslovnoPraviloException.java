package com.example.autonaj2.service;

// baca se kada zahtev krsi poslovno pravilo (npr. termin je zauzet, email vec postoji...)
public class PoslovnoPraviloException extends RuntimeException {

    public PoslovnoPraviloException(String poruka) {
        super(poruka);
    }
}
