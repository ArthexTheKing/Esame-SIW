package it.uniroma3.siw.springcertificazioni.model;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Esame {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Future
    @NotNull
    private LocalDateTime data;

    @NotNull
    @Positive
    private Integer durata;

    @NotBlank
    @Size(min = 2, max = 10)
    private String aula;

    @OneToMany(mappedBy = "esame", cascade = REMOVE)
    private List<Prenotazione> prenotati;

    @ManyToOne
    private Certificazione certificazione;

    public static boolean isOggiGiornoPrimaDiEsame(Esame esame) {
        return LocalDateTime.now().isAfter(esame.getData().minusDays(1l));
    }
    
}
