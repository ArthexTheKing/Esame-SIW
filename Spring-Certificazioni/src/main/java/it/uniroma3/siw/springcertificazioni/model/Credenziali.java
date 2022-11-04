package it.uniroma3.siw.springcertificazioni.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.uniroma3.siw.springcertificazioni.model.enumeration.Ruolo;
import lombok.Data;

@Data
@Entity
public class Credenziali {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column(length = 64)
    private String password;

    @Enumerated(STRING)
    @Column(length = 10)
    private Ruolo ruolo;

    @OneToOne(cascade = ALL)
    private Studente studente;
    
}
