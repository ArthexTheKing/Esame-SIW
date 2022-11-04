package it.uniroma3.siw.springcertificazioni.model;

import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataPrenotazione;

    @ManyToOne
    private Studente studente;

    @ManyToOne
    private Esame esame;
    
}
