package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.mvc.validation.settings.DissectionDiagnoseNameValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author lukaszkaleta
 * @since 22.07.13 14:23
 */
@Component
@Qualifier("dissectionDiagnoseOptionValidator")
public class DissectionDiagnoseOptionValidator extends DissectionDiagnoseNameValidator {

    @Override
    protected void rejectLatin(Errors errors) {
        errors.rejectValue("name.latin", "protocol.dissection.diagnose.option.name.latin.empty.error");
    }

    @Override
    protected void rejectTranslated(Errors errors) {
        errors.rejectValue("name.translated", "protocol.dissection.diagnose.option.name.translated.empty.error");
    }
}
