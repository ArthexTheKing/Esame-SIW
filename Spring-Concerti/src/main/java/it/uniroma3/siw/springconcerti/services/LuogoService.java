package it.uniroma3.siw.springconcerti.services;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springconcerti.model.Luogo;
import it.uniroma3.siw.springconcerti.repository.LuogoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LuogoService {

    private final LuogoRepository luogoRepository;

    @Transactional
    public void salvaLuogo(Luogo luogo) {
        this.luogoRepository.save(luogo);
    }

    public List<Luogo> getLuoghi() {
        return stream(this.luogoRepository.findAll().spliterator(), false).collect(toList());
    }

    public Luogo getLuogo(Long id) {
        return this.luogoRepository.findById(id).get();
    }

    public void cancellaLuogo(Long id) {
        this.luogoRepository.deleteById(id);
    }

    public boolean esisteLuogo(String nome, String citta) {
        return this.luogoRepository.existsByNomeAndCitta(nome, citta);
    }
    
}
