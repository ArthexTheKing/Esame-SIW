package it.uniroma3.siw.springconcerti.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springconcerti.model.Prenotazione;
import it.uniroma3.siw.springconcerti.model.Utente;

@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

    public List<Prenotazione> findByAcquirente(Utente acquirente);
    
}
