package it.uniroma3.siw.springcertificazioni.controller;

import static it.uniroma3.siw.springcertificazioni.model.Esame.isOggiGiornoPrimaDiEsame;
import static java.time.LocalDateTime.now;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.springcertificazioni.controller.components.UserDetailsComponent;
import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.model.Prenotazione;
import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import it.uniroma3.siw.springcertificazioni.service.PrenotazioneService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PrenotazioneController {

    private final EsameService esameService;
    private final PrenotazioneService prenotazioneService;
    private final UserDetailsComponent userDetailsComponent;

    @GetMapping("/prenotazioni")
    public String mostraPrenotazioniStudente(
            @RequestParam(value = "prenotazioneEffettuata", required = false) boolean prenotazioneEffettuata,
            @RequestParam(value = "prenotazioneAnnullata", required = false) boolean prenotazioneAnnullata,        
            @RequestParam(value = "prenotazioneNonAnnullata", required = false) Boolean prenotazioneNonAnnullata,
            Model model) {
        Studente studenteAttuale = this.userDetailsComponent.getStudenteCorrente();
        model.addAttribute("prenotazioni", studenteAttuale.getPrenotazioni());
        model.addAttribute("prenotazioneEffettuata", prenotazioneEffettuata);
        model.addAttribute("prenotazioneNonAnnullata", prenotazioneNonAnnullata);
        model.addAttribute("prenotazioneAnnullata", prenotazioneAnnullata);
        return "prenotazioni";
    }

    @GetMapping("/prenotazioni/{id}/delete")
    public String annullaPrenotazione(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Prenotazione prenotazione = this.prenotazioneService.getPrenotazione(id);
        Esame esamePrenotato = prenotazione.getEsame();
        if (isOggiGiornoPrimaDiEsame(esamePrenotato)) {
            redirectAttributes.addAttribute("prenotazioneNonAnnullata", true);
            return "redirect:/prenotazioni";
        }
        this.prenotazioneService.cancellaPrenotazione(id);
        redirectAttributes.addAttribute("prenotazioneAnnullata", true);
        return "redirect:/prenotazioni";
    }

    @GetMapping("/prenotazioni/esame/{id}")
    public String effettuaPrenotazione(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Studente studenteAttuale = this.userDetailsComponent.getStudenteCorrente();
        Esame esame = this.esameService.getEsame(id);
        Certificazione certificazione = esame.getCertificazione();
        if (isOggiGiornoPrimaDiEsame(esame)) {
            /* Se la giornata odierna è il giorno prima dell'esame */
            redirectAttributes.addAttribute("prenotazioneFallita", true);
            return "redirect:/certificazioni/" + certificazione.getId();
        }
        if(this.prenotazioneService.esistePrenotazioneAdEsame(esame, studenteAttuale)) {
            redirectAttributes.addAttribute("giaPrenotato", true);
            return "redirect:/certificazioni/" + certificazione.getId();
        }
        /* Se non è il giorno prima dell'esame */
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataPrenotazione(now());
        prenotazione.setEsame(esame);
        prenotazione.setStudente(studenteAttuale);
        this.prenotazioneService.salvaPrenotazione(prenotazione);
        redirectAttributes.addAttribute("prenotazioneEffettuata", true);
        return "redirect:/prenotazioni";
    }

}
