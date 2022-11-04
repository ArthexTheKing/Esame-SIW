package it.uniroma3.siw.springconcerti.model;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private LocalDateTime dataPrenotazione;

    @OneToOne
    private Biglietto biglietto;

    @ManyToOne(cascade = MERGE)
    private Utente acquirente;

    public Prenotazione(Biglietto biglietto, Utente utente) {
        this.dataPrenotazione = LocalDateTime.now();
        this.biglietto = biglietto;
        this.acquirente = utente;
    }
    
}
