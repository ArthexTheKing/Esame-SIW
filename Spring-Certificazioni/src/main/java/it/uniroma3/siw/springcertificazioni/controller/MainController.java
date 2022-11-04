package it.uniroma3.siw.springcertificazioni.controller;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.springcertificazioni.controller.components.UserDetailsComponent;
import it.uniroma3.siw.springcertificazioni.model.Studente;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private static final Logger log = getLogger(MainController.class);

    private final UserDetailsComponent userDetailsComponent;

    @GetMapping(value = {"/", "/index"})
    public String getIndex(@RequestParam(required = false) boolean error, Model model) {
        log.info("Richiesta GET /index");
        model.addAttribute("error", error);
        log.info("Caricamento pagina index.html");
        return "index";
    }

    @GetMapping("/profilo")
    public String getProfiloStudente(Model model) {
        Studente studente = this.userDetailsComponent.getStudenteCorrente();
        model.addAttribute("studente", studente);
        return "studente";
    }
    
}
