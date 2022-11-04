package it.uniroma3.siw.springconcerti.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.springconcerti.controllers.components.validators.LuogoValidator;
import it.uniroma3.siw.springconcerti.model.Luogo;
import it.uniroma3.siw.springconcerti.services.LuogoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LuogoController {

    private static final Logger log = LoggerFactory.getLogger(LuogoController.class);

    private final LuogoService luogoService;
    private final LuogoValidator luogoValidator;

    @GetMapping("/admin/luoghi")
    public String getLuoghi(Model model) {
        log.info("Richiesta GET /admin/luoghi");
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        return "admin/luoghi";
    }

    @GetMapping("/admin/luoghi/new")
    public String newLuogo(Model model) {
        log.info("Richiesta GET /admin/luoghi/new");
        model.addAttribute("luogo", new Luogo());
        return "admin/luogoForm";
    }

    @GetMapping("/admin/luoghi/{id}/delete")
    public String deleteLuogo(@PathVariable Long id) {
        log.info("Richiesta GET /admin/luoghi/" + id + "/delete");
        this.luogoService.cancellaLuogo(id);
        return "redirect:/admin/luoghi";
    }

    @PostMapping("/admin/luoghi/new")
    public String saveLuogo(@Valid @ModelAttribute Luogo luogo, BindingResult bindingResult, Model model) {
        log.info("Richiesta POST /admin/luoghi/new");
        this.luogoValidator.validate(luogo, bindingResult);

        if(!bindingResult.hasErrors()) {
            log.info("Parametri inseriti Corretti");
            this.luogoService.salvaLuogo(luogo);
            return "redirect:/admin/luoghi";
        }
        log.info("Parametri inseriti non Validi");
        return "admin/luogoForm";
    }
    
}
