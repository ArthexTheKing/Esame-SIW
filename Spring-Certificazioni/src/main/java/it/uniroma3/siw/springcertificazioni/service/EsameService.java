package it.uniroma3.siw.springcertificazioni.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.repository.EsameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EsameService {

    private final EsameRepository esameRepository;

    @Transactional
    public Esame salvaEsame(Esame esame) {
        return this.esameRepository.save(esame);
    }

    @Transactional
    public void cancellaEsame(Long id) {
        this.esameRepository.deleteById(id);
    }

    public Esame getEsame(Long id) {
        return this.esameRepository.findById(id).get();
    }

    public boolean esisteEsame(Long id, String aula, LocalDateTime dataDa, Integer durata) {
        if(aula == null || dataDa == null || durata == null) return false;
        LocalDateTime dataA = dataDa.plusMinutes(durata.longValue());
        return this.esameRepository.existsByIdNotAndAulaAndDataBetween(id, aula, dataDa, dataA);
    }
    
}
