package com.ra.dissection.protocol.mvc.validation.settings;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author lukaszkaleta
 * @since 20.07.13 13:36
 */
@Component
@Qualifier("dissectionDiagnoseSourceOptionValidator")
public class DissectionDiagnoseSourceOptionValidator extends DissectionDiagnoseNameValidator {

    @Override
    protected void rejectLatin(Errors errors) {
        errors.rejectValue("name.latin", "dissection.diagnose.source.option.name.latin.empty.error");
    }

    @Override
    protected void rejectTranslated(Errors errors) {
        errors.rejectValue("name.translated", "dissection.diagnose.source.option.name.translated.empty.error");
    }
}
