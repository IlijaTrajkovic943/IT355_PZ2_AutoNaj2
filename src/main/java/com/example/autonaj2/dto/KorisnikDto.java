package com.example.autonaj2.dto;

import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Uloga;

// DTO koji se vraca klijentu - namerno nema polje lozinka, ne zelimo da se hes ikad vrati u odgovoru
public class KorisnikDto {

    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private Uloga uloga;

    public static KorisnikDto fromEntity(Korisnik korisnik) {
        KorisnikDto dto = new KorisnikDto();
        dto.setId(korisnik.getId());
        dto.setIme(korisnik.getIme());
        dto.setPrezime(korisnik.getPrezime());
        dto.setEmail(korisnik.getEmail());
        dto.setUloga(korisnik.getUloga());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
}
