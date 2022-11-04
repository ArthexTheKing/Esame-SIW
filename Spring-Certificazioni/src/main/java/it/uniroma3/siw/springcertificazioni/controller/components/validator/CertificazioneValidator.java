package it.uniroma3.siw.springcertificazioni.controller.components.validator;

import static it.uniroma3.siw.springcertificazioni.controller.components.validator.constant.ErrorCodeConstant.UNIQUE_CODE;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Certificazione;
import it.uniroma3.siw.springcertificazioni.service.CertificazioneService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CertificazioneValidator implements Validator {

    private static final Logger log = getLogger(CertificazioneValidator.class);

    private final CertificazioneService certificazioneService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Inizio Validazione Certificazione");
        Certificazione certificazione = (Certificazione)target;
        String nome = certificazione.getNome();
        if(this.certificazioneService.esisteCertificazione(nome)) {
            log.warn("Certificazione Già Esistente");
            errors.rejectValue("nome", UNIQUE_CODE);
        }
        log.info("Fine Validazione Certificazione");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Certificazione.class.equals(clazz);
    }
    
}
