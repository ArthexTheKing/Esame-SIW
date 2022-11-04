package it.uniroma3.siw.springcertificazioni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Certificazione;

@Repository
public interface CertificazioneRepository extends CrudRepository<Certificazione, Long> {

    public boolean existsByNome(String nome);
    
}
