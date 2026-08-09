package com.example.autonaj2.dto;

// odgovor nakon uspesne prijave/registracije - nosi JWT token i osnovne podatke o korisniku
public class PrijavaOdgovorDto {

    private String token;
    private String email;
    private String uloga;

    public PrijavaOdgovorDto(String token, String email, String uloga) {
        this.token = token;
        this.email = email;
        this.uloga = uloga;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }
}
