package it.uniroma3.siw.springcertificazioni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.model.Prenotazione;
import it.uniroma3.siw.springcertificazioni.model.Studente;

@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

    public boolean existsByEsameAndStudente(Esame esame, Studente studente);
    
}
