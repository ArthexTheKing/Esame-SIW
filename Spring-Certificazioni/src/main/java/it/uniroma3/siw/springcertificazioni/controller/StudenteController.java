package it.uniroma3.siw.springcertificazioni.controller;

import static it.uniroma3.siw.springcertificazioni.model.enumeration.Ruolo.STUDENTE;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.springcertificazioni.controller.components.validator.CredenzialiValidator;
import it.uniroma3.siw.springcertificazioni.controller.components.validator.StudenteValidator;
import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.model.Indirizzo;
import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import it.uniroma3.siw.springcertificazioni.service.StudenteService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/studenti")
public class StudenteController {

    private static final Logger log = getLogger(StudenteController.class);

    private final CredenzialiService credenzialiService;
    private final StudenteService studenteService;

    private final CredenzialiValidator credenzialiValidator;
    private final StudenteValidator studenteValidator;

    @GetMapping
    public String getStudenti(
            @RequestParam(value = "cancellazione", required = false) Boolean cancellazione,
            @RequestParam(value = "immatricolato", required = false) Boolean immatricolato,
            Model model) {
        log.info("Richiesta GET /admin/studenti");
        List<Studente> studenti = this.studenteService.getStudenti();
        model.addAttribute("cancellazione", cancellazione);
        model.addAttribute("immatricolato", immatricolato);
        model.addAttribute("studenti", studenti);
        return "admin/studenti";
    }

    @PostMapping
    public String postNewStudente(
            @Valid @ModelAttribute("credenziali") Credenziali credenziali,
            BindingResult credenzialiBR,
            @Valid @ModelAttribute("studente") Studente studente,
            BindingResult studenteBR,
            @Valid @ModelAttribute("indirizzo") Indirizzo indirizzo,
            BindingResult indirizzoBR,
            RedirectAttributes redirectAttributes) {
        log.info("Richiesta POST /admin/studenti");
        this.credenzialiValidator.validate(credenziali, credenzialiBR);
        this.studenteValidator.validate(studente, studenteBR);
        if (!credenzialiBR.hasErrors() && !studenteBR.hasErrors() && !indirizzoBR.hasErrors()) {
            log.info("Nessun Errore di Validazione");
            studente.setIndirizzo(indirizzo);
            credenziali.setStudente(studente);
            credenziali.setRuolo(STUDENTE);
            this.credenzialiService.salvaCredenziali(credenziali);
            redirectAttributes.addAttribute("immatricolato", true);
            return "redirect:/admin/studenti";
        }
        log.warn("Errori di Validazione");
        return "admin/forms/studenteForm";
    }

    @GetMapping("/new")
    public String getNewStudente(Model model) {
        log.info("Richiesta GET /admin/studenti/new");
        model.addAttribute("credenziali", new Credenziali());
        model.addAttribute("studente", new Studente());
        model.addAttribute("indirizzo", new Indirizzo());
        return "admin/forms/studenteForm";
    }

    @GetMapping("/{id}")
    public String getStudente(
            @RequestParam(value = "modifica", required = false) Boolean modifica,
            @PathVariable Long id,
            Model model) {
        log.info("Richiesta GET /admin/studenti/" + id);
        Studente studente = this.studenteService.getStudente(id);
        model.addAttribute("studente", studente);
        model.addAttribute("modifica", modifica);
        return "studente";
    }

    @GetMapping("/{id}/put")
    public String getPutStudente(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/studenti/" + id + "/put");
        Studente studente = this.studenteService.getStudente(id);
        model.addAttribute("studente", studente);
        model.addAttribute("indirizzo", studente.getIndirizzo());
        return "admin/forms/modificaStudenteForm";
    }

    @PostMapping("/{id}/put")
    public String putStudente(
            @PathVariable Long id,
            @Valid @ModelAttribute("studente") Studente studente,
            BindingResult studenteBR,
            @Valid @ModelAttribute("indirizzo") Indirizzo indirizzo,
            BindingResult indirizzoBR,
            RedirectAttributes redirectAttributes) {
        log.info("Richiesta POST /admin/utenti/" + id + "/put");
        this.studenteValidator.validate(studente, studenteBR);
        if (!studenteBR.hasErrors() && !indirizzoBR.hasErrors()) {
            log.info("Nessun Errore di Validazione");
            studente.setIndirizzo(indirizzo);
            this.studenteService.salvaStudente(studente);
            redirectAttributes.addAttribute("modifica", true);
            return "redirect:/admin/studenti/" + id;
        }
        log.warn("Errori di Validazione");
        return "admin/forms/modificaStudenteForm";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudente(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        log.info("Richiesta GET /admin/studenti/" + id + "/delete");
        Studente studente = this.studenteService.getStudente(id);
        Credenziali credenziali = this.credenzialiService.getCredenziali(studente);
        this.credenzialiService.cancellaCredenziali(credenziali.getId());
        redirectAttributes.addAttribute("cancellazione", true);
        return "redirect:/admin/studenti";
    }

}
