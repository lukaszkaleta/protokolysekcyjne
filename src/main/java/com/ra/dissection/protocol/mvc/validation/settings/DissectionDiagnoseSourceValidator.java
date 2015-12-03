package com.ra.dissection.protocol.mvc.validation.settings;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author lukaszkaleta
 * @since 20.07.13 13:33
 */
@Component
@Qualifier("dissectionDiagnoseSourceValidator")
public class DissectionDiagnoseSourceValidator extends DissectionDiagnoseNameValidator {

    @Override
    protected void rejectLatin(Errors errors) {
        errors.rejectValue("name.latin", "dissection.diagnose.name.latin.empty.error");
    }

    @Override
    protected void rejectTranslated(Errors errors) {
        errors.rejectValue("name.translated", "dissection.diagnose.name.translated.empty.error");
    }
}
