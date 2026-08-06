package com.example.autonaj2.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// ukupnaCena i status se ne primaju od klijenta - racuna/postavlja ih servisni sloj
public class RezervacijaRequestDto {

    @NotNull(message = "Korisnik je obavezan")
    private Long korisnikId;

    @NotNull(message = "Automobil je obavezan")
    private Long automobilId;

    @NotNull(message = "Datum od je obavezan")
    @FutureOrPresent(message = "Datum od ne moze biti u proslosti")
    private LocalDate datumOd;

    @NotNull(message = "Datum do je obavezan")
    @FutureOrPresent(message = "Datum do ne moze biti u proslosti")
    private LocalDate datumDo;

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Long getAutomobilId() {
        return automobilId;
    }

    public void setAutomobilId(Long automobilId) {
        this.automobilId = automobilId;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }
}
