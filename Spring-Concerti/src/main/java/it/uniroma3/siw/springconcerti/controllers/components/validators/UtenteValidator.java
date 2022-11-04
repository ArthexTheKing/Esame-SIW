package it.uniroma3.siw.springconcerti.controllers.components.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springconcerti.model.Utente;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UtenteValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(UtenteValidator.class);

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validazione Utente Iniziata");
        log.info("Validazione Utente Terminata");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }
    
}
