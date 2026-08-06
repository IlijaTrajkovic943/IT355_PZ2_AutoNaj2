package com.example.autonaj2.dto;

import jakarta.validation.constraints.NotBlank;

public class KategorijaVozilaRequestDto {

    @NotBlank(message = "Naziv je obavezan")
    private String naziv;

    private String opis;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
