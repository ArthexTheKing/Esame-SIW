package it.uniroma3.siw.springcertificazioni.controller.components;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsComponent {

    private final CredenzialiService credenzialiService;

    public Credenziali getCredenzialiAutenticate() {
        UserDetails userDetails = (UserDetails) getContext().getAuthentication().getPrincipal();
        return this.credenzialiService.getCredenziali(userDetails.getUsername());
    }

    public Studente getStudenteCorrente() {
        return this.getCredenzialiAutenticate().getStudente();
    }
    
}
