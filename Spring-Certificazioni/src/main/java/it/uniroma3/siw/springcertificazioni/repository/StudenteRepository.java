package it.uniroma3.siw.springcertificazioni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Studente;

@Repository
public interface StudenteRepository extends CrudRepository<Studente, Long> {

    public boolean existsByIdNotAndEmail(Long id, String email);
    public boolean existsByIdNotAndNumeroDiTelefono(Long id, String numeroDiTelefono);
    
}
