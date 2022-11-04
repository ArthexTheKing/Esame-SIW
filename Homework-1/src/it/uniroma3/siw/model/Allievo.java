package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Allievo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	private LocalDate dataDiNascita;
	
	private String luogoDiNascita;
	
	@Column(nullable = false)
	private int matricola;
	
	private String email;
	
	/*
	 * Quando io immetto un allievo nel Db è corretto anche immettere la società di appartenenza
	 * ma non ha senso rimuoverla perche altri alievi potrebbero appartenere alla stessa società
	 */
	@OneToOne(cascade = {CascadeType.PERSIST})
	private Società società;
	
	@ManyToMany
	private List<Corso> corsi;
	
	public Allievo() {
		
	}
}
