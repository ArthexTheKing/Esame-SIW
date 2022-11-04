package it.uniroma3.siw.springcertificazioni.controller.components.validator;

import static it.uniroma3.siw.springcertificazioni.controller.components.validator.constant.ErrorCodeConstant.SIZE_CODE;
import static it.uniroma3.siw.springcertificazioni.controller.components.validator.constant.ErrorCodeConstant.UNIQUE_CODE;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Credenziali;
import it.uniroma3.siw.springcertificazioni.service.CredenzialiService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CredenzialiValidator implements Validator {

    private static final Logger log = getLogger(CredenzialiValidator.class);

    private static final Integer SIZE_PASSWORD_MIN = 6;
    private static final Integer SIZE_PASSWORD_MAX = 20;

    private final CredenzialiService credenzialiService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Inizio Validazione Credenziali");
        Credenziali credenziali = (Credenziali)target;
        String username = credenziali.getUsername();
        String password = credenziali.getPassword();
        if(this.credenzialiService.esisteCredenziali(username)) {
            log.warn("Username non Disponibile");
            errors.rejectValue("username", UNIQUE_CODE);
        }
        if(password.length() < SIZE_PASSWORD_MIN || password.length() > SIZE_PASSWORD_MAX) {
            log.warn("Password troppo corta o troppo lunga");
            errors.rejectValue("password", SIZE_CODE);
        }
        log.info("Fine Validazione Credenziali");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Credenziali.class.equals(clazz);
    }
    
}
