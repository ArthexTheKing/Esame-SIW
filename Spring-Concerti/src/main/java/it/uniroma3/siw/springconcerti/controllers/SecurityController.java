package it.uniroma3.siw.springconcerti.controllers;

import static it.uniroma3.siw.springconcerti.model.enumeration.Ruolo.ADMIN;
import static it.uniroma3.siw.springconcerti.model.enumeration.Ruolo.UTENTE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.springconcerti.controllers.components.UserDetailsComponent;
import it.uniroma3.siw.springconcerti.controllers.components.validators.CredenzialiValidator;
import it.uniroma3.siw.springconcerti.controllers.components.validators.UtenteValidator;
import it.uniroma3.siw.springconcerti.model.Credenziali;
import it.uniroma3.siw.springconcerti.model.Utente;
import it.uniroma3.siw.springconcerti.services.CredenzialiService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final CredenzialiService credenzialiService;
    private final CredenzialiValidator credenzialiValidator;
    private final UtenteValidator utenteValidator;
    private final UserDetailsComponent userDetailsComponent;

    @GetMapping("/login")
    public String getLoginForm(@RequestParam(required = false) boolean error, Model model) {
        log.info("Richiesta GET /login");
        if (error) {
            log.warn("Credenziali inserite Errate");
            model.addAttribute("loginError", true);
        }
        return "auth/loginForm";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        log.info("Richiesta GET /register");
        model.addAttribute("utente", new Utente());
        model.addAttribute("credenziali", new Credenziali());
        return "auth/registraUtenteForm";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        log.info("Richiesta GET /logout");
        return "redirect:/index";
    }

    @GetMapping("/default")
    public String defaultAutenticato(Model model) {
        log.info("Richiesta GET /default");
        Credenziali credenziali = this.userDetailsComponent.getCredenzialiAutenticate();
        log.info("Indirizzamento alla Home per il ruolo: " + credenziali.getRuolo().toString());
        return (credenziali.getRuolo().equals(ADMIN)) ? "redirect:/admin/home" : "redirect:/home";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("utente") Utente utente,
            BindingResult utenteBindingResult,
            @Valid @ModelAttribute("credenziali") Credenziali credenziali,
            BindingResult credenzialiBindingResult,
            Model model) {
        /* Validazione */
        log.info("Richiesta POST /register");
        this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
        this.utenteValidator.validate(utente, utenteBindingResult);

        if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
            log.info("Parametri inseriti Corretti");
            credenziali.setRuolo(UTENTE);
            credenziali.setUtente(utente);
            this.credenzialiService.salvaCredenziali(credenziali);
            log.info("Registrazione Completata");
            return "auth/esitoRegistrazione";
        }
        log.info("Parametri inseriti non Validi");
        return "auth/registraUtenteForm";
    }

}
