package com.example.turisticka_agencija.repository;

import com.example.turisticka_agencija.model.Korisnik;
import com.example.turisticka_agencija.model.UlogaKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    Optional<Korisnik> findByEmail(String email);

    List<Korisnik> findByUloga(UlogaKorisnika uloga);

    List<Korisnik> findByAktivanTrue();
}