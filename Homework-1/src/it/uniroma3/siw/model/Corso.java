package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

@Entity
public class Corso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	private LocalDate dataDiInizio;
	
	private int durata;
	
	@ManyToMany(mappedBy = "corsi")
	private List<Allievo> studenti;
	
	@ManyToOne
	private Docente curatore;
	
	public Corso() {
		
	}
}
