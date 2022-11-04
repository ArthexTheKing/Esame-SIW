package it.uniroma3.siw.springconcerti.services;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springconcerti.model.Concerto;
import it.uniroma3.siw.springconcerti.model.Luogo;
import it.uniroma3.siw.springconcerti.repository.ConcertoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertoService {

    private final ConcertoRepository concertoRepository;

    @Transactional
    public Concerto salvaConcerto(Concerto concerto) {
        return this.concertoRepository.save(concerto);
    }

    @Transactional
    public void cancellaConcerto(Long id) {
        this.concertoRepository.deleteById(id);
    }

    public List<Concerto> getConcerti() {
        return stream(this.concertoRepository.findAll().spliterator(), false).collect(toList());
    }

    public List<Concerto> getConcerti(LocalDate dataDa, LocalDate dataA) {
        return this.concertoRepository.findTop6ByDataBetweenOrderByData(dataDa, dataA);
    }

    public Pair<Integer, List<Concerto>> getConcerti(Luogo luogo, LocalDate dataDa, LocalDate dataA) {
        Integer nConcerti = 0;
        List<Concerto> concerti = null;
        if(luogo == null && dataDa == null && dataA == null) {
            nConcerti = this.contaConcerti();
            concerti = this.getConcerti();
        }
        if(luogo == null && dataDa == null && dataA != null) {
            nConcerti = this.concertoRepository.countByDataBetween(LocalDate.now(), dataA);
            concerti = this.concertoRepository.findByDataBetween(LocalDate.now(), dataA);
        }
        if(luogo == null && dataDa != null && dataA == null) {
            nConcerti = this.concertoRepository.countByDataGreaterThanEqual(dataDa);
            concerti = this.concertoRepository.findByDataGreaterThanEqual(dataDa);
        }
        if(luogo == null && dataDa != null && dataA != null) {
            nConcerti = this.concertoRepository.countByDataBetween(dataDa, dataA);
            concerti = this.concertoRepository.findByDataBetween(dataDa, dataA);
        }
        if(luogo != null && dataDa == null && dataA == null) {
            nConcerti = this.concertoRepository.countByLuogo(luogo);
            concerti = this.concertoRepository.findByLuogo(luogo);
        }
        if(luogo != null && dataDa == null && dataA != null) {
            nConcerti = this.concertoRepository.countByLuogoAndDataBetween(luogo, LocalDate.now(), dataA);
            concerti = this.concertoRepository.findByLuogoAndDataBetween(luogo, LocalDate.now(), dataA);
        }
        if(luogo != null && dataDa != null && dataA == null) {
            nConcerti = this.concertoRepository.countByLuogoAndDataGreaterThanEqual(luogo, dataDa);
            concerti = this.concertoRepository.findByLuogoAndDataGreaterThanEqual(luogo, dataDa);
        }
        if(luogo != null && dataDa != null && dataA != null) {
            nConcerti = this.concertoRepository.countByLuogoAndDataBetween(luogo, dataDa, dataA);
            concerti = this.concertoRepository.findByLuogoAndDataBetween(luogo, dataDa, dataA);
        }
        return Pair.of(nConcerti, concerti);
    }

    public Concerto getConcerto(Long id) {
        return this.concertoRepository.findById(id).get();
    }

    public int contaConcerti() {
        return toIntExact(this.concertoRepository.count());
    }

    public boolean esisteConcerto(Long id, LocalDate data, Luogo luogo) {
        return this.concertoRepository.existsByIdNotAndDataAndLuogo(id, data, luogo);
    }
    
}
