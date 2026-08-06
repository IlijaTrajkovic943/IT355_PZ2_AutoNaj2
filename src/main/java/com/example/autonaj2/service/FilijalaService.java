package com.example.autonaj2.service;

import com.example.autonaj2.dto.FilijalaRequestDto;
import com.example.autonaj2.entity.Filijala;
import com.example.autonaj2.repository.FilijalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// readOnly = true na nivou klase - Hibernate zna da ne mora da prati promene na objektima (brze),
// a metode koje nesto menjaju u bazi pojedinacno oznacavamo sa @Transactional da otvore "pravu" transakciju
@Service
@Transactional(readOnly = true)
public class FilijalaService {

    private final FilijalaRepository filijalaRepository;

    public FilijalaService(FilijalaRepository filijalaRepository) {
        this.filijalaRepository = filijalaRepository;
    }

    public List<Filijala> pronadjiSve() {
        return filijalaRepository.findAll();
    }

    public Filijala pronadjiPoId(Long id) {
        return filijalaRepository.findById(id)
                .orElseThrow(() -> new NijeNadjenoException("Filijala sa id " + id + " ne postoji"));
    }

    @Transactional
    public Filijala kreiraj(FilijalaRequestDto dto) {
        Filijala filijala = new Filijala();
        filijala.setNaziv(dto.getNaziv());
        filijala.setGrad(dto.getGrad());
        filijala.setAdresa(dto.getAdresa());
        return filijalaRepository.save(filijala);
    }

    @Transactional
    public Filijala izmeni(Long id, FilijalaRequestDto dto) {
        Filijala filijala = pronadjiPoId(id);
        filijala.setNaziv(dto.getNaziv());
        filijala.setGrad(dto.getGrad());
        filijala.setAdresa(dto.getAdresa());
        return filijalaRepository.save(filijala);
    }

    @Transactional
    public void obrisi(Long id) {
        Filijala filijala = pronadjiPoId(id);
        filijalaRepository.delete(filijala);
    }
}
