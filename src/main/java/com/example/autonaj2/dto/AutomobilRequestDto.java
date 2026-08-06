package com.example.autonaj2.dto;

import java.math.BigDecimal;

// klijent salje samo ID kategorije/filijale, servisni sloj ce ih pronaci preko repozitorijuma
public class AutomobilRequestDto {

    private String marka;
    private String model;
    private Integer godiste;
    private String registarskiBroj;
    private BigDecimal cenaPoDanu;
    private boolean dostupan;
    private Long kategorijaId;
    private Long filijalaId;

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getGodiste() {
        return godiste;
    }

    public void setGodiste(Integer godiste) {
        this.godiste = godiste;
    }

    public String getRegistarskiBroj() {
        return registarskiBroj;
    }

    public void setRegistarskiBroj(String registarskiBroj) {
        this.registarskiBroj = registarskiBroj;
    }

    public BigDecimal getCenaPoDanu() {
        return cenaPoDanu;
    }

    public void setCenaPoDanu(BigDecimal cenaPoDanu) {
        this.cenaPoDanu = cenaPoDanu;
    }

    public boolean isDostupan() {
        return dostupan;
    }

    public void setDostupan(boolean dostupan) {
        this.dostupan = dostupan;
    }

    public Long getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(Long kategorijaId) {
        this.kategorijaId = kategorijaId;
    }

    public Long getFilijalaId() {
        return filijalaId;
    }

    public void setFilijalaId(Long filijalaId) {
        this.filijalaId = filijalaId;
    }
}
