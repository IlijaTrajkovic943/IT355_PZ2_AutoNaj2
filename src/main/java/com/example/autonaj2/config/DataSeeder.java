package com.example.autonaj2.config;

import com.example.autonaj2.entity.Automobil;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Rezervacija;
import com.example.autonaj2.entity.StatusRezervacije;
import com.example.autonaj2.entity.Uloga;
import com.example.autonaj2.repository.AutomobilRepository;
import com.example.autonaj2.repository.FilijalaRepository;
import com.example.autonaj2.repository.KategorijaVozilaRepository;
import com.example.autonaj2.repository.KorisnikRepository;
import com.example.autonaj2.repository.RezervacijaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

// CommandLineRunner - metoda run() se izvrsava automatski jednom, odmah nakon sto se aplikacija pokrene
@Component
public class DataSeeder implements CommandLineRunner {

    private final KorisnikRepository korisnikRepository;
    private final FilijalaRepository filijalaRepository;
    private final KategorijaVozilaRepository kategorijaVozilaRepository;
    private final AutomobilRepository automobilRepository;
    private final RezervacijaRepository rezervacijaRepository;

    public DataSeeder(KorisnikRepository korisnikRepository,
                       FilijalaRepository filijalaRepository,
                       KategorijaVozilaRepository kategorijaVozilaRepository,
                       AutomobilRepository automobilRepository,
                       RezervacijaRepository rezervacijaRepository) {
        this.korisnikRepository = korisnikRepository;
        this.filijalaRepository = filijalaRepository;
        this.kategorijaVozilaRepository = kategorijaVozilaRepository;
        this.automobilRepository = automobilRepository;
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public void run(String... args) {
        // seed se pokrece samo ako je baza prazna, da se podaci ne dupliraju pri svakom restartu
        if (korisnikRepository.count() > 0) {
            return;
        }

        Korisnik admin = new Korisnik();
        admin.setIme("Petar");
        admin.setPrezime("Petrovic");
        admin.setEmail("admin@autonaj.rs");
        admin.setLozinka("admin123");
        admin.setUloga(Uloga.ROLE_ADMIN);
        korisnikRepository.save(admin);

        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Marko");
        korisnik.setPrezime("Markovic");
        korisnik.setEmail("marko@autonaj.rs");
        korisnik.setLozinka("user123");
        korisnik.setUloga(Uloga.ROLE_USER);
        korisnikRepository.save(korisnik);

        Filijala beograd = new Filijala();
        beograd.setNaziv("AutoNaj Beograd");
        beograd.setGrad("Beograd");
        beograd.setAdresa("Bulevar Kralja Aleksandra 73");
        filijalaRepository.save(beograd);

        Filijala noviSad = new Filijala();
        noviSad.setNaziv("AutoNaj Novi Sad");
        noviSad.setGrad("Novi Sad");
        noviSad.setAdresa("Bulevar Oslobodjenja 12");
        filijalaRepository.save(noviSad);

        KategorijaVozila ekonomska = new KategorijaVozila();
        ekonomska.setNaziv("Ekonomska");
        ekonomska.setOpis("Mali gradski automobili, najniza cena");
        kategorijaVozilaRepository.save(ekonomska);

        KategorijaVozila srednja = new KategorijaVozila();
        srednja.setNaziv("Srednja klasa");
        srednja.setOpis("Automobili srednje velicine, dobar odnos cene i udobnosti");
        kategorijaVozilaRepository.save(srednja);

        KategorijaVozila premium = new KategorijaVozila();
        premium.setNaziv("Premium");
        premium.setOpis("Luksuzna vozila viseg cenovnog ranga");
        kategorijaVozilaRepository.save(premium);

        Automobil golf = new Automobil();
        golf.setMarka("Volkswagen");
        golf.setModel("Golf 7");
        golf.setGodiste(2019);
        golf.setRegistarskiBroj("BG-123-AA");
        golf.setCenaPoDanu(new BigDecimal("35.00"));
        golf.setDostupan(true);
        golf.setKategorija(ekonomska);
        golf.setFilijala(beograd);
        automobilRepository.save(golf);

        Automobil passat = new Automobil();
        passat.setMarka("Volkswagen");
        passat.setModel("Passat B8");
        passat.setGodiste(2021);
        passat.setRegistarskiBroj("BG-456-BB");
        passat.setCenaPoDanu(new BigDecimal("55.00"));
        passat.setDostupan(true);
        passat.setKategorija(srednja);
        passat.setFilijala(beograd);
        automobilRepository.save(passat);

        Automobil mercedes = new Automobil();
        mercedes.setMarka("Mercedes-Benz");
        mercedes.setModel("E-Klasa");
        mercedes.setGodiste(2022);
        mercedes.setRegistarskiBroj("NS-789-CC");
        mercedes.setCenaPoDanu(new BigDecimal("95.00"));
        mercedes.setDostupan(true);
        mercedes.setKategorija(premium);
        mercedes.setFilijala(noviSad);
        automobilRepository.save(mercedes);

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnik);
        rezervacija.setAutomobil(golf);
        rezervacija.setDatumOd(LocalDate.now().plusDays(1));
        rezervacija.setDatumDo(LocalDate.now().plusDays(4));
        rezervacija.setUkupnaCena(new BigDecimal("105.00"));
        rezervacija.setStatus(StatusRezervacije.POTVRDJENA);
        rezervacijaRepository.save(rezervacija);
    }
}
