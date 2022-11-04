package it.uniroma3.siw.springconcerti.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springconcerti.model.Credenziali;

@Repository
public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {

    public Optional<Credenziali> findByUsername(String username);
	public boolean existsByUsername(String username);
    
}
