package com.example.autonaj2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO koji klijent salje prilikom prijave (login)
public class PrijavaRequestDto {

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije validnog formata")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    private String lozinka;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}
