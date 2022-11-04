package it.uniroma3.siw.springcertificazioni.controller;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    private static final Logger log = getLogger(SecurityController.class);

    @GetMapping("/login")
    public String getLogin() {
        log.info("Richiesta GET /login");
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String getLogout() {
        log.info("Richiesta GET /logout");
        return "redirect:/index";
    }

    @GetMapping("/home")
    public String getHome() {
        log.info("Richiesta GET /home");
        return "home";
    }
    
}
