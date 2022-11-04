package it.uniroma3.siw.springconcerti.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.springconcerti.model.Concerto;
import it.uniroma3.siw.springconcerti.model.Luogo;

@Repository
public interface ConcertoRepository extends CrudRepository<Concerto, Long> {

    public boolean existsByIdNotAndDataAndLuogo(Long id, LocalDate data, Luogo luogo);

    public List<Concerto> findTop6ByDataBetweenOrderByData(LocalDate prima, LocalDate dopo);

    public int countByLuogo(Luogo luogo);
    public List<Concerto> findByLuogo(Luogo luogo);

    public int countByDataGreaterThanEqual(LocalDate dataDa);
    public List<Concerto> findByDataGreaterThanEqual(LocalDate dataDa);

    public int countByDataBetween(LocalDate dataDa, LocalDate dataA);
    public List<Concerto> findByDataBetween(LocalDate prima, LocalDate dopo);

    public int countByLuogoAndDataGreaterThanEqual(Luogo luogo, LocalDate dataDa);
    public List<Concerto> findByLuogoAndDataGreaterThanEqual(Luogo luogo, LocalDate dataDa);

    public int countByLuogoAndDataBetween(Luogo luogo, LocalDate dataDa, LocalDate dataA);
    public List<Concerto> findByLuogoAndDataBetween(Luogo luogo, LocalDate dataDa, LocalDate dataA);
    
}
