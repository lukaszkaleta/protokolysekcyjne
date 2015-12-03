package com.ra.dissection.protocol.mvc.validation.settings;

import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseName;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v40 18.05.13 22:00
 */
public abstract class DissectionDiagnoseNameValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DissectionDiagnoseName.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DissectionDiagnoseName dissectionDiagnoseName = (DissectionDiagnoseName) target;
        String latin = dissectionDiagnoseName.getLatin();
        if (latin == null || latin.trim().isEmpty()) {
            rejectLatin(errors);
        }
        String translated = dissectionDiagnoseName.getTranslated();
        if (translated == null || translated.trim().isEmpty()) {
            rejectTranslated(errors);
        }
    }

    protected abstract void rejectLatin(Errors errors);
    protected abstract void rejectTranslated(Errors errors);
}
