package it.uniroma3.siw.springcertificazioni.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Studente;

@Repository
public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {

    public boolean existsByUsername(String username);
    public Optional<Credenziali> findByStudente(Studente studente);
    public Optional<Credenziali> findByUsername(String username);
    
}
