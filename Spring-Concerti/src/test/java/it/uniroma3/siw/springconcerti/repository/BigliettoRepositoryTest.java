package it.uniroma3.siw.springconcerti.repository;

import static it.uniroma3.siw.springconcerti.model.enumeration.TipoBiglietto.INTERO;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import it.uniroma3.siw.springconcerti.model.Biglietto;
import it.uniroma3.siw.springconcerti.model.Concerto;
import it.uniroma3.siw.springconcerti.model.Luogo;
import it.uniroma3.siw.springconcerti.model.enumeration.TipoBiglietto;

@DataJpaTest
public class BigliettoRepositoryTest {

    private static final String NOME_LUOGO = "nome";
    private static final String CITTA_LUOGO = "città";

    private static final String NOME_CONCERTO = "nome";
    private static final LocalDate DATA_CONCERTO = LocalDate.now().plusDays(1l);
    private static final LocalTime ORARIO_CONCERTO = LocalTime.now();

    private static final String NOME_BIGLIETTO = "nome";
    private static final TipoBiglietto TIPO_BIGLIETTO = INTERO;
    private static final Float PREZZO_BIGLIETTO = 0f;
    private static final Integer QUANTITA_BIGLIETTO = 0;

    @Autowired
    private BigliettoRepository bigliettoRepository;

    @Autowired
    private ConcertoRepository concertoRepository;

    @Autowired
    private LuogoRepository luogoRepository;

    private Luogo luogo;
    private Concerto concerto;

    @BeforeEach
    public void setUp() {
        /* Crea un luogo nel database */
        Luogo luogo = new Luogo(NOME_LUOGO, CITTA_LUOGO);
        this.luogo = this.luogoRepository.save(luogo);
        /* Crea un concerto nel database */
        Concerto concerto = new Concerto(NOME_CONCERTO, DATA_CONCERTO, ORARIO_CONCERTO);
        concerto.setLuogo(this.luogo);
        this.concerto = this.concertoRepository.save(concerto);
    }

    @AfterEach
    public void tearDown() {
        this.bigliettoRepository.deleteAll();
        this.concertoRepository.deleteAll();
        this.luogoRepository.deleteAll();
    }

    @Test
    public void testExistsByNomeAndTipoBigliettoAndConcerto() {
        Biglietto biglietto = new Biglietto(NOME_BIGLIETTO, TIPO_BIGLIETTO, PREZZO_BIGLIETTO, QUANTITA_BIGLIETTO);
        biglietto.setConcerto(this.concerto);
        this.concerto.getBiglietti().add(biglietto);

        this.concertoRepository.save(concerto); // Per la politica di Cascade ALL, anche biglietto si salva

        boolean risultato = this.bigliettoRepository.existsByNomeAndConcerto(NOME_BIGLIETTO, this.concerto);

        assertThat(risultato).isTrue();
    }

}
