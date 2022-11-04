package it.uniroma3.siw.springcertificazioni.controller.components.validator;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.springcertificazioni.model.Esame;
import it.uniroma3.siw.springcertificazioni.service.EsameService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EsameValidator implements Validator {
    
    private static final Logger log = getLogger(EsameValidator.class);

    private final EsameService esameService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Inizio Validazione Esame");
        Esame esame = (Esame)target;
        Long id = (esame.getId() == null) ? 0l : esame.getId();
        String aula = esame.getAula();
        LocalDateTime data = esame.getData();
        Integer durata = esame.getDurata();
        if(this.esameService.esisteEsame(id, aula, data, durata)) {
            log.warn("L'esame si sovrappone con un altro esame");
            errors.reject("Overlap.esame");
        }
        log.info("Fine Validazione Esame");
    }

    @Override
    public boolean supports(Class<?> clazz) {
       return Esame.class.equals(clazz);
    }

}
