package com.example.autonaj2.dto;

// oblik u kom se greske vracaju klijentu (umesto standardnog Spring-ovog html/whitelabel ekrana)
public class GreskaOdgovor {

    private int status;
    private String poruka;

    public GreskaOdgovor(int status, String poruka) {
        this.status = status;
        this.poruka = poruka;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}
