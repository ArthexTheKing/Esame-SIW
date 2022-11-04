package it.uniroma3.siw.springcertificazioni.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.repository.CertificazioneRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificazioneService {

    private final CertificazioneRepository certificazioneRepository;

    @Transactional
    public Certificazione salvaCertificazione(Certificazione certificazione) {
        return this.certificazioneRepository.save(certificazione);
    }

    @Transactional
    public void cancellaCertificazione(Long id) {
        this.certificazioneRepository.deleteById(id);
    }

    public Certificazione getCertificazione(Long id) {
        return this.certificazioneRepository.findById(id).get();
    }

    public List<Certificazione> getCertificazioni() {
        return stream(this.certificazioneRepository.findAll().spliterator(), false).collect(toList());
    }

    public boolean esisteCertificazione(String nome) {
        return this.certificazioneRepository.existsByNome(nome);
    }
    
}
