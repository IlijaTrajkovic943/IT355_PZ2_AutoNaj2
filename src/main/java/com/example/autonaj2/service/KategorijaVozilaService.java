package com.example.autonaj2.service;

import com.example.autonaj2.dto.KategorijaVozilaRequestDto;
import com.example.autonaj2.entity.KategorijaVozila;
import com.example.autonaj2.repository.KategorijaVozilaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class KategorijaVozilaService {

    private final KategorijaVozilaRepository kategorijaVozilaRepository;

    public KategorijaVozilaService(KategorijaVozilaRepository kategorijaVozilaRepository) {
        this.kategorijaVozilaRepository = kategorijaVozilaRepository;
    }

    public List<KategorijaVozila> pronadjiSve() {
        return kategorijaVozilaRepository.findAll();
    }

    public KategorijaVozila pronadjiPoId(Long id) {
        return kategorijaVozilaRepository.findById(id)
                .orElseThrow(() -> new NijeNadjenoException("Kategorija sa id " + id + " ne postoji"));
    }

    @Transactional
    public KategorijaVozila kreiraj(KategorijaVozilaRequestDto dto) {
        if (kategorijaVozilaRepository.existsByNaziv(dto.getNaziv())) {
            throw new PoslovnoPraviloException("Kategorija sa nazivom '" + dto.getNaziv() + "' vec postoji");
        }
        KategorijaVozila kategorija = new KategorijaVozila();
        kategorija.setNaziv(dto.getNaziv());
        kategorija.setOpis(dto.getOpis());
        return kategorijaVozilaRepository.save(kategorija);
    }

    @Transactional
    public KategorijaVozila izmeni(Long id, KategorijaVozilaRequestDto dto) {
        KategorijaVozila kategorija = pronadjiPoId(id);
        kategorija.setNaziv(dto.getNaziv());
        kategorija.setOpis(dto.getOpis());
        return kategorijaVozilaRepository.save(kategorija);
    }

    @Transactional
    public void obrisi(Long id) {
        KategorijaVozila kategorija = pronadjiPoId(id);
        kategorijaVozilaRepository.delete(kategorija);
    }
}
