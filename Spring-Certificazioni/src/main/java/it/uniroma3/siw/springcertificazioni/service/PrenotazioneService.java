package it.uniroma3.siw.springcertificazioni.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.model.Prenotazione;
import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    @Transactional
    public Prenotazione salvaPrenotazione(Prenotazione prenotazione) {
        return this.prenotazioneRepository.save(prenotazione);
    }

    @Transactional
    public void cancellaPrenotazione(Long id) {
        this.prenotazioneRepository.deleteById(id);
    }

    public Prenotazione getPrenotazione(Long id) {
        return this.prenotazioneRepository.findById(id).get();
    }

    public boolean esistePrenotazioneAdEsame(Esame esame, Studente studente) {
        return this.prenotazioneRepository.existsByEsameAndStudente(esame, studente);
    }
    
}
