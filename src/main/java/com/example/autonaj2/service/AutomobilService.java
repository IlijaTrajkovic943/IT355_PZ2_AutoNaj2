package com.example.autonaj2.service;

import com.example.autonaj2.dto.AutomobilRequestDto;
import com.example.autonaj2.entity.Automobil;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.repository.AutomobilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AutomobilService {

    private final AutomobilRepository automobilRepository;
    private final KategorijaVozilaService kategorijaVozilaService;
    private final FilijalaService filijalaService;

    public AutomobilService(AutomobilRepository automobilRepository,
                             KategorijaVozilaService kategorijaVozilaService,
                             FilijalaService filijalaService) {
        this.automobilRepository = automobilRepository;
        this.kategorijaVozilaService = kategorijaVozilaService;
        this.filijalaService = filijalaService;
    }

    public List<Automobil> pronadjiSve() {
        return automobilRepository.findAll();
    }

    public Automobil pronadjiPoId(Long id) {
        return automobilRepository.findById(id)
                .orElseThrow(() -> new NijeNadjenoException("Automobil sa id " + id + " ne postoji"));
    }

    @Transactional
    public Automobil kreiraj(AutomobilRequestDto dto) {
        if (automobilRepository.existsByRegistarskiBroj(dto.getRegistarskiBroj())) {
            throw new PoslovnoPraviloException(
                    "Automobil sa registarskim brojem '" + dto.getRegistarskiBroj() + "' vec postoji");
        }
        // pronadjiPoId ovde baca NijeNadjenoException ako prosledjeni kategorijaId/filijalaId ne postoji
        KategorijaVozila kategorija = kategorijaVozilaService.pronadjiPoId(dto.getKategorijaId());
        Filijala filijala = filijalaService.pronadjiPoId(dto.getFilijalaId());

        Automobil automobil = new Automobil();
        popuniPolja(automobil, dto, kategorija, filijala);
        return automobilRepository.save(automobil);
    }

    @Transactional
    public Automobil izmeni(Long id, AutomobilRequestDto dto) {
        Automobil automobil = pronadjiPoId(id);
        KategorijaVozila kategorija = kategorijaVozilaService.pronadjiPoId(dto.getKategorijaId());
        Filijala filijala = filijalaService.pronadjiPoId(dto.getFilijalaId());
        popuniPolja(automobil, dto, kategorija, filijala);
        return automobilRepository.save(automobil);
    }

    @Transactional
    public void obrisi(Long id) {
        Automobil automobil = pronadjiPoId(id);
        automobilRepository.delete(automobil);
    }

    private void popuniPolja(Automobil automobil, AutomobilRequestDto dto,
                              KategorijaVozila kategorija, Filijala filijala) {
        automobil.setMarka(dto.getMarka());
        automobil.setModel(dto.getModel());
        automobil.setGodiste(dto.getGodiste());
        automobil.setRegistarskiBroj(dto.getRegistarskiBroj());
        automobil.setCenaPoDanu(dto.getCenaPoDanu());
        automobil.setDostupan(dto.isDostupan());
        automobil.setKategorija(kategorija);
        automobil.setFilijala(filijala);
    }
}
