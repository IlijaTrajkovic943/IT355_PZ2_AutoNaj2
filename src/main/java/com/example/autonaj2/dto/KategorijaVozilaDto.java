package com.example.autonaj2.dto;

import com.example.autonaj2.entity.KategorijaVozila;

public class KategorijaVozilaDto {

    private Long id;
    private String naziv;
    private String opis;

    public static KategorijaVozilaDto fromEntity(KategorijaVozila kategorija) {
        KategorijaVozilaDto dto = new KategorijaVozilaDto();
        dto.setId(kategorija.getId());
        dto.setNaziv(kategorija.getNaziv());
        dto.setOpis(kategorija.getOpis());
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
