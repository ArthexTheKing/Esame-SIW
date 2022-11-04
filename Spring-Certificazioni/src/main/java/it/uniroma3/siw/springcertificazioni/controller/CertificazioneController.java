package it.uniroma3.siw.springcertificazioni.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.springcertificazioni.controller.components.validator.CertificazioneValidator;
import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.service.CertificazioneService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CertificazioneController {

    private static final Logger log = getLogger(CertificazioneController.class);

    private final CertificazioneService certificazioneService;
    private final CertificazioneValidator certificazioneValidator;

    @GetMapping("/certificazioni")
    public String getCertificazioni(
            @RequestParam(value = "creata", required = false) Boolean creata,
            @RequestParam(value = "cancellazione", required = false) Boolean cancellazione,
            Model model) {
        log.info("Richiesta GET /certificazioni");
        List<Certificazione> certificazioni = this.certificazioneService.getCertificazioni();
        model.addAttribute("creata", creata);
        model.addAttribute("cancellazione", cancellazione);
        model.addAttribute("certificazioni", certificazioni);
        return "certificazioni";
    }

    @GetMapping("/certificazioni/{id}")
    public String getCertificazione(
            @PathVariable Long id,
            @RequestParam(value = "cancellazione", required = false) Boolean cancellazione,
            @RequestParam(value = "esameCreato", required = false) Boolean esameCreato,
            @RequestParam(value = "prenotazioneFallita", required = false) Boolean prenotazioneFallita,
            @RequestParam(value = "giaPrenotato", required = false) boolean isGiaPrenotato,
            Model model) {
        log.info("Richiesta GET /certificazioni/" + id);
        Certificazione certificazione = this.certificazioneService.getCertificazione(id);
        model.addAttribute("certificazione", certificazione);
        model.addAttribute("cancellazione", cancellazione);
        model.addAttribute("prenotazioneFallita", prenotazioneFallita);
        model.addAttribute("esameCreato", esameCreato);
        model.addAttribute("giaPrenotato", isGiaPrenotato);
        return "certificazione";
    }

    @PostMapping("/admin/certificazioni")
    public String postNewCertificazioni(
            @Valid @ModelAttribute Certificazione certificazione,
            BindingResult certificazioneBR,
            RedirectAttributes redirectAttributes) {
        log.info("Richiesta POST /admin/certificazioni");
        this.certificazioneValidator.validate(certificazione, certificazioneBR);
        if (!certificazioneBR.hasErrors()) {
            log.info("Nessun errore di Validazione");
            this.certificazioneService.salvaCertificazione(certificazione);
            redirectAttributes.addAttribute("creata", true);
            return "redirect:/certificazioni";
        }
        log.warn("Errori di Validazione");
        return "admin/forms/certificazioneForm";
    }

    @GetMapping("/admin/certificazioni/new")
    public String getNewCertificazioni(Model model) {
        log.info("Richiesta GET /admin/certificazioni/new");
        model.addAttribute("certificazione", new Certificazione());
        return "admin/forms/certificazioneForm";
    }

    @GetMapping("/admin/certificazioni/{id}/delete")
    public String deleteCertificazione(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        log.info("Richiesta GET /admin/certificazioni/" + id + "/delete");
        this.certificazioneService.cancellaCertificazione(id);
        redirectAttributes.addAttribute("cancellazione", true);
        return "redirect:/certificazioni";
    }

}
