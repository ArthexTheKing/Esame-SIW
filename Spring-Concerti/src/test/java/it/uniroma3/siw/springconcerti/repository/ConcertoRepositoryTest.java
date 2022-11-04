package it.uniroma3.siw.springconcerti.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import it.uniroma3.siw.springconcerti.model.Concerto;
import it.uniroma3.siw.springconcerti.model.Luogo;

@DataJpaTest
public class ConcertoRepositoryTest {

    private static final String NOME_LUOGO = "nome";
    private static final String CITTA_LUOGO = "città";

    private static final String NOME_CONCERTO = "nome";
    private static final LocalDate DATA_CONCERTO = LocalDate.now().plusDays(1l);
    private static final LocalTime ORARIO_CONCERTO = LocalTime.now();

    @Autowired
    private ConcertoRepository concertoRepository;

    @Autowired
    private LuogoRepository luogoRepository;

    private Luogo luogo;

    @BeforeEach
    public void setUp() {
        Luogo luogo = new Luogo(NOME_LUOGO, CITTA_LUOGO);
        this.luogo = this.luogoRepository.save(luogo);
    }

    @AfterEach
    public void tearDown() {
        this.concertoRepository.deleteAll();
        this.luogoRepository.deleteAll();
    }

    @Test
    public void testExistsByIdNotAndDataAndLuogo() {
        Concerto concerto = new Concerto(NOME_CONCERTO, DATA_CONCERTO, ORARIO_CONCERTO);
        concerto.setLuogo(this.luogo);
        
        this.concertoRepository.save(concerto);

        boolean risultato = this.concertoRepository.existsByIdNotAndDataAndLuogo(0l, DATA_CONCERTO, this.luogo);

        assertThat(risultato).isTrue();
    }

}
