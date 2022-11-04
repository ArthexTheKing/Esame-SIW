package it.uniroma3.siw.springcertificazioni.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Studente {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String nome;

    @NotBlank
    @Size(min = 2, max = 20)
    private String cognome;

    @Email
    @NotBlank
    @Size(min = 10, max = 20)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(unique = true)
    private String numeroDiTelefono;

    @Past
    @NotNull
    private LocalDate dataDiNascita;

    @OneToOne(cascade = ALL)
    private Indirizzo indirizzo;

    @OneToMany(cascade = {MERGE, REMOVE}, mappedBy = "studente")
    private List<Prenotazione> prenotazioni;
    
}
