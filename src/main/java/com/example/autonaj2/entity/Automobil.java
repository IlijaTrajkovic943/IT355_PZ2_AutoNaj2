package com.example.autonaj2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "automobil")
public class Automobil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marka;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer godiste;

    @Column(nullable = false, unique = true)
    private String registarskiBroj;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cenaPoDanu;

    @Column(nullable = false)
    private boolean dostupan;

    // vise automobila moze pripadati jednoj kategoriji (Ekonomska, SUV, Premium...)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategorija_id", nullable = false)
    private KategorijaVozila kategorija;

    // vise automobila moze biti smesteno u jednoj filijali
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filijala_id", nullable = false)
    private Filijala filijala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public KategorijaVozila getKategorija() {
        return kategorija;
    }

    public void setKategorija(KategorijaVozila kategorija) {
        this.kategorija = kategorija;
    }

    public Filijala getFilijala() {
        return filijala;
    }

    public void setFilijala(Filijala filijala) {
        this.filijala = filijala;
    }
}
