package com.example.autonaj2.dto;

import jakarta.validation.constraints.NotBlank;

public class FilijalaRequestDto {

    @NotBlank(message = "Naziv je obavezan")
    private String naziv;

    @NotBlank(message = "Grad je obavezan")
    private String grad;

    @NotBlank(message = "Adresa je obavezna")
    private String adresa;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
