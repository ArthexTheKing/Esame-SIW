package it.uniroma3.siw.springcertificazioni.controller.components.validator;

import static it.uniroma3.siw.springcertificazioni.controller.components.validator.constant.ErrorCodeConstant.UNIQUE_CODE;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Studente;
import it.uniroma3.siw.springcertificazioni.service.StudenteService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudenteValidator implements Validator {

    private static final Logger log = getLogger(StudenteValidator.class);
    
    private final StudenteService utenteService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Inizio Validazione Utente");
        Studente utente = (Studente)target;
        Long id = (utente.getId() == null) ? 0l : utente.getId();
        String email = utente.getEmail();
        String numeroDiTelefono = utente.getNumeroDiTelefono();
        if(this.utenteService.esisteStudenteEmail(id, email)) {
            log.warn("Email già utilizzata");
            errors.rejectValue("email", UNIQUE_CODE);
        }
        if(this.utenteService.esisteStudenteNumeroDiTelefono(id, numeroDiTelefono)) {
            log.warn("Numero di Telefono già utilizzato");
            errors.rejectValue("numeroDiTelefono", UNIQUE_CODE);
        }
        log.info("Fine Validazione Utente");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Studente.class.equals(clazz);
    }
    
}
