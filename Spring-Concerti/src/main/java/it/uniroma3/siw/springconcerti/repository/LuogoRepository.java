package it.uniroma3.siw.springconcerti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springconcerti.model.Luogo;

@Repository
public interface LuogoRepository extends CrudRepository<Luogo, Long> {
   
    public boolean existsByNomeAndCitta(String nome, String citta);

}
