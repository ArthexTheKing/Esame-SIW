package it.uniroma3.siw.springcertificazioni.model;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.AUTO;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Certificazione {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(unique = true)
    private String nome;

    @NotBlank
    @Size(min = 2, max = 30)
    private String areaDisciplinare;

    @OneToMany(mappedBy = "certificazione", cascade = REMOVE)
    private List<Esame> esami;
    
}
