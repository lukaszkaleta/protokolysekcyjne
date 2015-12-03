package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.mvc.validation.settings.DissectionDiagnoseNameValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author lukaszkaleta
 * @since 20.07.13 13:40
 */
@Component
@Qualifier("dissectionDiagnoseValidator")
public class DissectionDiagnoseValidator extends DissectionDiagnoseNameValidator {

    @Override
    protected void rejectLatin(Errors errors) {
        errors.rejectValue("dissectionDiagnose.name.latin", "dissection.diagnose.name.latin.empty.error");
    }

    @Override
    protected void rejectTranslated(Errors errors) {
        errors.rejectValue("dissectionDiagnose.name.translated", "dissection.diagnose.name.translated.empty.error");
    }
}
