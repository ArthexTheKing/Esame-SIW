package it.uniroma3.siw.springcertificazioni.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springcertificazioni.model.Esame;

@Repository
public interface EsameRepository extends CrudRepository<Esame, Long> {

    public boolean existsByIdNotAndAulaAndDataBetween(Long id, String aula, LocalDateTime dataDa, LocalDateTime dataA);

}
