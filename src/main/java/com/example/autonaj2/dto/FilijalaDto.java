package com.example.autonaj2.dto;

import com.example.autonaj2.entity.Filijala;

public class FilijalaDto {

    private Long id;
    private String naziv;
    private String grad;
    private String adresa;

    public static FilijalaDto fromEntity(Filijala filijala) {
        FilijalaDto dto = new FilijalaDto();
        dto.setId(filijala.getId());
        dto.setNaziv(filijala.getNaziv());
        dto.setGrad(filijala.getGrad());
        dto.setAdresa(filijala.getAdresa());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
