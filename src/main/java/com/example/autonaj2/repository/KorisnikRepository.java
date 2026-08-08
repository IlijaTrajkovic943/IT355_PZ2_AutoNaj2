package com.example.autonaj2.repository;

import com.example.autonaj2.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    boolean existsByEmail(String email);

    Optional<Korisnik> findByEmail(String email);
}
