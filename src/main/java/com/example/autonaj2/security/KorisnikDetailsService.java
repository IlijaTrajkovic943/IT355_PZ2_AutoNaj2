package com.example.autonaj2.security;

import com.example.autonaj2.entity.Korisnik;
import com.example.autonaj2.repository.KorisnikRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService - Spring Security ovo koristi da po emailu ucita korisnika i njegovu ulogu
@Service
public class KorisnikDetailsService implements UserDetailsService {

    private final KorisnikRepository korisnikRepository;

    public KorisnikDetailsService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik sa email adresom '" + email + "' ne postoji"));
        return User.builder()
                .username(korisnik.getEmail())
                .password(korisnik.getLozinka())
                .authorities(korisnik.getUloga().name())
                .build();
    }
}
