package it.uniroma3.siw.springcertificazioni.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Ruolo {
    
    SEGRETERIA("Segreteria"),
    STUDENTE("Studente");

    @Getter
    private final String nome;

}
