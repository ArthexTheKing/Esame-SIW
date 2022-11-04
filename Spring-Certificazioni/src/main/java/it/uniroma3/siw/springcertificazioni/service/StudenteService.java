package it.uniroma3.siw.springcertificazioni.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.repository.StudenteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudenteService {

    private final StudenteRepository studenteRepository;

    @Transactional
    public Studente salvaStudente(Studente studente) {
        return this.studenteRepository.save(studente);
    }

    @Transactional
    public void cancellaStudente(Long id) {
        this.studenteRepository.deleteById(id);
    }

    public Studente getStudente(Long id) {
        return this.studenteRepository.findById(id).get();
    }

    public List<Studente> getStudenti() {
        return stream(this.studenteRepository.findAll().spliterator(), false).collect(toList());
    }

    public boolean esisteStudenteEmail(Long id, String email) {
        return this.studenteRepository.existsByIdNotAndEmail(id, email);
    }

    public boolean esisteStudenteNumeroDiTelefono(Long id, String numeroDiTelefono) {
        return this.studenteRepository.existsByIdNotAndNumeroDiTelefono(id, numeroDiTelefono);
    }
    
}
