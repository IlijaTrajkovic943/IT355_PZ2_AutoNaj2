package com.example.autonaj2.repository;

import com.example.autonaj2.entity.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {

    // dva termina se preklapaju ako pocetak jednog pada pre kraja drugog i obrnuto
    // (klasicna formula za presek dva vremenska intervala), otkazane rezervacije se ne racunaju
    @Query("SELECT r FROM Rezervacija r WHERE r.automobil.id = :automobilId "
            + "AND r.status <> com.example.autonaj2.entity.StatusRezervacije.OTKAZANA "
            + "AND r.datumOd <= :datumDo AND r.datumDo >= :datumOd")
    List<Rezervacija> pronadjiPreklapajuce(@Param("automobilId") Long automobilId,
                                            @Param("datumOd") LocalDate datumOd,
                                            @Param("datumDo") LocalDate datumDo);
}
