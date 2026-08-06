package com.example.autonaj2.repository;

import com.example.autonaj2.entity.KategorijaVozila;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategorijaVozilaRepository extends JpaRepository<KategorijaVozila, Long> {

    boolean existsByNaziv(String naziv);
}
