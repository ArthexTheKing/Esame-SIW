package it.uniroma3.siw.springconcerti.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.uniroma3.siw.springconcerti.model.enumeration.TipoBiglietto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Biglietto {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @Enumerated(STRING)
    @Column(length = 8)
    private TipoBiglietto tipo;

    @Min(0)
    @NotNull
    private Float prezzo;

    @Min(0)
    @NotNull
    private Integer quantita;

    @ManyToOne
    private Concerto concerto;

    public Biglietto(String nome, TipoBiglietto tipo, Float prezzo, Integer quantita) {
        this.nome = nome;
        this.tipo = tipo;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public static void riduciQuantita(Biglietto biglietto) {
        Integer quantita = biglietto.getQuantita() - 1;
        biglietto.setQuantita(quantita);
    }

    public static void aumentaQuantita(Biglietto biglietto) {
        Integer quantita = biglietto.getQuantita() + 1;
        biglietto.setQuantita(quantita);
    }
    
}
