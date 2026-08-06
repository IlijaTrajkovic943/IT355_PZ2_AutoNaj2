package com.example.autonaj2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

// klijent salje samo ID kategorije/filijale, servisni sloj ce ih pronaci preko repozitorijuma
public class AutomobilRequestDto {

    @NotBlank(message = "Marka je obavezna")
    private String marka;

    @NotBlank(message = "Model je obavezan")
    private String model;

    @NotNull(message = "Godiste je obavezno")
    @Min(value = 1950, message = "Godiste ne moze biti pre 1950")
    private Integer godiste;

    @NotBlank(message = "Registarski broj je obavezan")
    private String registarskiBroj;

    @NotNull(message = "Cena po danu je obavezna")
    @Positive(message = "Cena po danu mora biti veca od nule")
    private BigDecimal cenaPoDanu;

    private boolean dostupan;

    @NotNull(message = "Kategorija je obavezna")
    private Long kategorijaId;

    @NotNull(message = "Filijala je obavezna")
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
