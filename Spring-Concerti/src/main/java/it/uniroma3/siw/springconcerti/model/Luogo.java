package it.uniroma3.siw.springconcerti.model;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Luogo {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String nome;

    @NotBlank
    @Size(min = 2, max = 32)
    private String citta;

    public Luogo(String nome, String citta) {
        this.nome = nome;
        this.citta = citta;
    }
    
}
