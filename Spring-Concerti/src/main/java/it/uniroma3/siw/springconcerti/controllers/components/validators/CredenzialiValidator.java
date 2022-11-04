package it.uniroma3.siw.springconcerti.controllers.components.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springconcerti.model.Credenziali;
import it.uniroma3.siw.springconcerti.services.CredenzialiService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CredenzialiValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(CredenzialiValidator.class);

    private final CredenzialiService credenzialiService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validazione Credenziali Iniziata");
        Credenziali credenziali = (Credenziali)target;
        String username = credenziali.getUsername().trim();
        String password = credenziali.getPassword().trim();

        log.debug("Validazione campo username");
        if(this.credenzialiService.esistonoCredenziali(username)) {
            log.debug("Username già utilizzato");
            errors.rejectValue("username", "Unique");
        }
        
        log.debug("Validazione campo password");
        if(password.length() < 6 || password.length() > 20) {
            log.debug("Campo password non rispetta lunghezza");
            errors.rejectValue("password", "Size");
        }
        
        log.info("Validazione Credenziali Terminata");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Credenziali.class.equals(clazz);
    }

    
}
