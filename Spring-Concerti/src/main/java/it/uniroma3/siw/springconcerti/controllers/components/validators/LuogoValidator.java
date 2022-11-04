package it.uniroma3.siw.springconcerti.controllers.components.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springconcerti.model.Luogo;
import it.uniroma3.siw.springconcerti.services.LuogoService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LuogoValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(LuogoValidator.class);

    private final LuogoService luogoService;
    
    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validazione Luogo Iniziata");
        Luogo luogo = (Luogo)target;

        log.debug("Validazione Globale");
        if(this.luogoService.esisteLuogo(luogo.getNome(), luogo.getCitta())) {
            log.debug("Non possono esistere più luoghi con lo stesso nome nella stessa città");
            errors.reject("Unique.luogo");
        }
        
        log.info("Validazione Luogo Terminata");
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Luogo.class.equals(clazz);
    }
    
}
