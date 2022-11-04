package it.uniroma3.siw.springcertificazioni.model;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Indirizzo {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String via;

    @NotBlank
    @Size(min = 2, max = 30)
    private String citta;

    @NotBlank
    @Size(min = 5, max = 5)
    private String cap;

}
