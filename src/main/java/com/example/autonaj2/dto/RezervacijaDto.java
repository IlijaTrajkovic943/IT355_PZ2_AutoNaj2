package com.example.autonaj2.dto;

import com.example.autonaj2.entity.Rezervacija;
import com.example.autonaj2.entity.StatusRezervacije;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RezervacijaDto {

    private Long id;
    private Long korisnikId;
    private String korisnikIme;
    private Long automobilId;
    private String automobilNaziv;
    private LocalDate datumOd;
    private LocalDate datumDo;
    private BigDecimal ukupnaCena;
    private StatusRezervacije status;

    public static RezervacijaDto fromEntity(Rezervacija rezervacija) {
        RezervacijaDto dto = new RezervacijaDto();
        dto.setId(rezervacija.getId());
        dto.setKorisnikId(rezervacija.getKorisnik().getId());
        dto.setKorisnikIme(rezervacija.getKorisnik().getIme() + " " + rezervacija.getKorisnik().getPrezime());
        dto.setAutomobilId(rezervacija.getAutomobil().getId());
        dto.setAutomobilNaziv(rezervacija.getAutomobil().getMarka() + " " + rezervacija.getAutomobil().getModel());
        dto.setDatumOd(rezervacija.getDatumOd());
        dto.setDatumDo(rezervacija.getDatumDo());
        dto.setUkupnaCena(rezervacija.getUkupnaCena());
        dto.setStatus(rezervacija.getStatus());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getKorisnikIme() {
        return korisnikIme;
    }

    public void setKorisnikIme(String korisnikIme) {
        this.korisnikIme = korisnikIme;
    }

    public Long getAutomobilId() {
        return automobilId;
    }

    public void setAutomobilId(Long automobilId) {
        this.automobilId = automobilId;
    }

    public String getAutomobilNaziv() {
        return automobilNaziv;
    }

    public void setAutomobilNaziv(String automobilNaziv) {
        this.automobilNaziv = automobilNaziv;
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

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public StatusRezervacije getStatus() {
        return status;
    }

    public void setStatus(StatusRezervacije status) {
        this.status = status;
    }
}
