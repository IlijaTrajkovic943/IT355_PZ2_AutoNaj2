package com.example.autonaj2.dto;

import java.time.LocalDate;

// ukupnaCena i status se ne primaju od klijenta - racuna/postavlja ih servisni sloj
public class RezervacijaRequestDto {

    private Long korisnikId;
    private Long automobilId;
    private LocalDate datumOd;
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
