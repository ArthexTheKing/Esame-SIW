package it.uniroma3.siw.springconcerti.model;

import static javax.persistence.GenerationType.AUTO;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String nome;

    @NotBlank
    @Size(min = 2, max = 20)
    private String cognome;

    @OneToMany(mappedBy = "acquirente")
    private List<Prenotazione> prenotazioni;

    public Utente() {
        this.prenotazioni = new LinkedList<>();
    }
    
}
