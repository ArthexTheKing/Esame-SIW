package it.uniroma3.siw.springconcerti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springconcerti.model.Biglietto;
import it.uniroma3.siw.springconcerti.model.Concerto;

@Repository
public interface BigliettoRepository extends CrudRepository<Biglietto, Long> {

    public boolean existsByNomeAndConcerto(String nome, Concerto concerto);

}
