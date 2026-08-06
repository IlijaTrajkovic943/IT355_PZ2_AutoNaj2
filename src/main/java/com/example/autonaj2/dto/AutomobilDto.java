package com.example.autonaj2.dto;

import com.example.autonaj2.entity.Automobil;

import java.math.BigDecimal;

// vezani entiteti (kategorija, filijala) se ne vracaju kao ceo objekat vec spljosteni
// u par polja - jednostavnije za frontend i izbegava se problem sa lenjim ucitavanjem
public class AutomobilDto {

    private Long id;
    private String marka;
    private String model;
    private Integer godiste;
    private String registarskiBroj;
    private BigDecimal cenaPoDanu;
    private boolean dostupan;
    private Long kategorijaId;
    private String kategorijaNaziv;
    private Long filijalaId;
    private String filijalaNaziv;

    public static AutomobilDto fromEntity(Automobil automobil) {
        AutomobilDto dto = new AutomobilDto();
        dto.setId(automobil.getId());
        dto.setMarka(automobil.getMarka());
        dto.setModel(automobil.getModel());
        dto.setGodiste(automobil.getGodiste());
        dto.setRegistarskiBroj(automobil.getRegistarskiBroj());
        dto.setCenaPoDanu(automobil.getCenaPoDanu());
        dto.setDostupan(automobil.isDostupan());
        dto.setKategorijaId(automobil.getKategorija().getId());
        dto.setKategorijaNaziv(automobil.getKategorija().getNaziv());
        dto.setFilijalaId(automobil.getFilijala().getId());
        dto.setFilijalaNaziv(automobil.getFilijala().getNaziv());
        return dto;
    }

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

    public Long getKategorijaId() {
        return kategorijaId;
    }

    public void setKategorijaId(Long kategorijaId) {
        this.kategorijaId = kategorijaId;
    }

    public String getKategorijaNaziv() {
        return kategorijaNaziv;
    }

    public void setKategorijaNaziv(String kategorijaNaziv) {
        this.kategorijaNaziv = kategorijaNaziv;
    }

    public Long getFilijalaId() {
        return filijalaId;
    }

    public void setFilijalaId(Long filijalaId) {
        this.filijalaId = filijalaId;
    }

    public String getFilijalaNaziv() {
        return filijalaNaziv;
    }

    public void setFilijalaNaziv(String filijalaNaziv) {
        this.filijalaNaziv = filijalaNaziv;
    }
}
