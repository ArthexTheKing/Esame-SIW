package it.uniroma3.siw.springcertificazioni.controller;

import static org.slf4j.LoggerFactory.getLogger;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.springcertificazioni.controller.components.validator.EsameValidator;
import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.service.CertificazioneService;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EsameController {

    private static final Logger log = getLogger(EsameController.class);

    private final EsameService esameService;
    private final EsameValidator esameValidator;
    private final CertificazioneService certificazioneService;

    @GetMapping("/admin/esami/{id}/modify")
    public String modifyEsame(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/esami/" + id + "/modify");
        Esame esame = this.esameService.getEsame(id);
        Certificazione certificazione = esame.getCertificazione();
        model.addAttribute("certificazione", certificazione);
        model.addAttribute("esame", esame);
        return "admin/forms/esameForm";
    }

    @GetMapping("/admin/esami/{id}/delete")
    public String deleteEsame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Richiesta GET /admin/esami/" + id + "/delete");
        Certificazione certificazione = this.esameService.getEsame(id).getCertificazione();
        this.esameService.cancellaEsame(id);
        redirectAttributes.addAttribute("cancellazione", true);
        return "redirect:/certificazioni/" + certificazione.getId();
    }

    @GetMapping("/admin/esami/certificazione/{id}")
    public String getCreaEsame(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/certificazioni/" + id + "/esame/new");
        Certificazione certificazione = this.certificazioneService.getCertificazione(id);
        model.addAttribute("certificazione", certificazione);
        model.addAttribute("esame", new Esame());
        return "admin/forms/esameForm";
    }

    @PostMapping("/admin/esami/certificazione/{id}")
    public String postCreaEsame(
            @Valid @ModelAttribute Esame esame,
            BindingResult bindingResult,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.info("Richiesta POST /admin/certificazioni/" + id + "/esame");
        Certificazione certificazione = this.certificazioneService.getCertificazione(id);
        certificazione.getEsami().add(esame);
        esame.setCertificazione(certificazione);
        this.esameValidator.validate(esame, bindingResult);
        if(!bindingResult.hasErrors()) {
            log.info("Nessun errore di Validazione");
            this.esameService.salvaEsame(esame);
            redirectAttributes.addAttribute("esameCreato", true);
            return "redirect:/certificazioni/" + id;
        }
        log.warn("Errori di Validazione");
        model.addAttribute("certificazione", certificazione);
        return "admin/forms/esameForm";
    }
    
}
