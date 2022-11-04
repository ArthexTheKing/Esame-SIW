package it.uniroma3.siw.springconcerti.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.springconcerti.model.Biglietto;
import it.uniroma3.siw.springconcerti.model.Concerto;
import it.uniroma3.siw.springconcerti.repository.BigliettoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BigliettoService {

    private final BigliettoRepository bigliettoRepository;

    @Transactional
    public Biglietto salvaBiglietto(Biglietto biglietto) {
        return this.bigliettoRepository.save(biglietto);
    }

    public Biglietto getBiglietto(Long id) {
        return this.bigliettoRepository.findById(id).get();
    }

    public boolean esisteBiglietto(String nome, Concerto concerto) {
        return this.bigliettoRepository.existsByNomeAndConcerto(nome, concerto);
    }
    
}
