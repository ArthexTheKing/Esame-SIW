package it.uniroma3.siw.springconcerti.controllers;

import static it.uniroma3.siw.springconcerti.controllers.components.constants.CurrencyConstants.SIMBOLO_MONETA;
import static java.time.LocalDate.now;
import static java.time.format.TextStyle.SHORT;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.springconcerti.controllers.components.UserDetailsComponent;
import it.uniroma3.siw.springconcerti.model.Concerto;
import it.uniroma3.siw.springconcerti.model.Luogo;
import it.uniroma3.siw.springconcerti.model.dto.RicercaDTO;
import it.uniroma3.siw.springconcerti.services.ConcertoService;
import it.uniroma3.siw.springconcerti.services.LuogoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private static final LocalDate DATA_DA = now();
    private static final LocalDate DATA_A = now().plusYears(1l);

    private final ConcertoService concertoService;
    private final LuogoService luogoService;

    private final UserDetailsComponent userDetailsComponent;

    @GetMapping(value = { "/", "/index" })
    public String index(Model model) {
        log.info("Richiesta GET /index");
        model.addAttribute("concerti", this.concertoService.getConcerti(DATA_DA, DATA_A));
        return "index";
    }

    @GetMapping("/profilo")
    public String mostraProfilo(Model model) {
        model.addAttribute("utente", this.userDetailsComponent.getUtenteAutenticato());
        model.addAttribute("valuta", SIMBOLO_MONETA);
        return "profilo";
    }

    @GetMapping("/home")
    public String homeUtente(Model model) {
        log.info("Richiesta GET /home");
        model.addAttribute("numeroConcerti", this.concertoService.contaConcerti());
        model.addAttribute("ricerca", new RicercaDTO());
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        model.addAttribute("stileData", SHORT);
        model.addAttribute("linguaData", Locale.getDefault());
        model.addAttribute("concerti", this.concertoService.getConcerti());
        return "home";
    }

    @GetMapping("/admin/home")
    public String getAdminHome() {
        log.info("Richiesta GET /admin/home");
        return "admin/home";
    }

    @PostMapping("/ricerca")
    public String homeUtenteFiltrata(@ModelAttribute("ricerca") RicercaDTO ricerca, Model model) {
        Luogo luogo = (ricerca.getLuogoId() != 0l) ? this.luogoService.getLuogo(ricerca.getLuogoId()) : null;
        Pair<Integer, List<Concerto>> concertiFiltrati = this.concertoService.getConcerti(luogo, ricerca.getDataDa(), ricerca.getDataA());
        int nConcerti = concertiFiltrati.getFirst();
        List<Concerto> concerti = concertiFiltrati.getSecond();
        model.addAttribute("numeroConcerti", nConcerti);
        model.addAttribute("ricerca", ricerca);
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        model.addAttribute("stileData", SHORT);
        model.addAttribute("linguaData", Locale.getDefault());
        model.addAttribute("concerti", concerti);
        return "home";
    }

}
