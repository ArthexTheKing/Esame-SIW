package it.uniroma3.siw.springcertificazioni.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.repository.CredenzialiRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredenzialiService {

    private final PasswordEncoder passwordEncoder;
    private final CredenzialiRepository credenzialiRepository;

    @Transactional
    public void salvaCredenziali(Credenziali credenziali) {
        credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
        this.credenzialiRepository.save(credenziali);
    }

    @Transactional
    public void cancellaCredenziali(Long id) {
        this.credenzialiRepository.deleteById(id);
    }

    public Credenziali getCredenziali(String username) {
        return this.credenzialiRepository.findByUsername(username).get();
    }

    public Credenziali getCredenziali(Studente studente) {
        return this.credenzialiRepository.findByStudente(studente).get();
    }

    public boolean esisteCredenziali(String username) {
        return this.credenzialiRepository.existsByUsername(username);
    }
    
}
