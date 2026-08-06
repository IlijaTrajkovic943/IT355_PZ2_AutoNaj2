package com.example.autonaj2.service;

import com.example.autonaj2.dto.RezervacijaRequestDto;
import com.example.autonaj2.entity.Automobil;
import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.entity.Rezervacija;
import com.example.autonaj2.entity.StatusRezervacije;
import com.example.autonaj2.repository.RezervacijaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RezervacijaService {

    private final RezervacijaRepository rezervacijaRepository;
    private final KorisnikService korisnikService;
    private final AutomobilService automobilService;

    public RezervacijaService(RezervacijaRepository rezervacijaRepository,
                               KorisnikService korisnikService,
                               AutomobilService automobilService) {
        this.rezervacijaRepository = rezervacijaRepository;
        this.korisnikService = korisnikService;
        this.automobilService = automobilService;
    }

    public List<Rezervacija> pronadjiSve() {
        return rezervacijaRepository.findAll();
    }

    public Rezervacija pronadjiPoId(Long id) {
        return rezervacijaRepository.findById(id)
                .orElseThrow(() -> new NijeNadjenoException("Rezervacija sa id " + id + " ne postoji"));
    }

    @Transactional
    public Rezervacija kreiraj(RezervacijaRequestDto dto) {
        if (!dto.getDatumOd().isBefore(dto.getDatumDo())) {
            throw new PoslovnoPraviloException("Datum do mora biti posle datuma od");
        }

        Korisnik korisnik = korisnikService.pronadjiPoId(dto.getKorisnikId());
        Automobil automobil = automobilService.pronadjiPoId(dto.getAutomobilId());

        // provera da li automobil vec ima rezervaciju koja se preklapa sa trazenim terminom
        List<Rezervacija> preklapanja = rezervacijaRepository.pronadjiPreklapajuce(
                automobil.getId(), dto.getDatumOd(), dto.getDatumDo());
        if (!preklapanja.isEmpty()) {
            throw new PoslovnoPraviloException(
                    "Automobil " + automobil.getMarka() + " " + automobil.getModel()
                            + " je vec rezervisan u tom terminu");
        }

        long brojDana = ChronoUnit.DAYS.between(dto.getDatumOd(), dto.getDatumDo());
        BigDecimal ukupnaCena = automobil.getCenaPoDanu().multiply(BigDecimal.valueOf(brojDana));

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnik);
        rezervacija.setAutomobil(automobil);
        rezervacija.setDatumOd(dto.getDatumOd());
        rezervacija.setDatumDo(dto.getDatumDo());
        rezervacija.setUkupnaCena(ukupnaCena);
        rezervacija.setStatus(StatusRezervacije.NA_CEKANJU);

        return rezervacijaRepository.save(rezervacija);
    }

    @Transactional
    public Rezervacija otkazi(Long id) {
        Rezervacija rezervacija = pronadjiPoId(id);
        rezervacija.setStatus(StatusRezervacije.OTKAZANA);
        return rezervacijaRepository.save(rezervacija);
    }

    @Transactional
    public void obrisi(Long id) {
        Rezervacija rezervacija = pronadjiPoId(id);
        rezervacijaRepository.delete(rezervacija);
    }
}
