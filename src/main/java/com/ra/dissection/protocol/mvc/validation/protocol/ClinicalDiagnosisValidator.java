package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v40 19.05.13 08:13
 */
@Component
@Qualifier("clinicalDiagnosisValidator")
public class ClinicalDiagnosisValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DissectionProtocol.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DissectionProtocol dissectionProtocol = (DissectionProtocol) target;
        String clinicalDiagnosis = dissectionProtocol.getClinicalDiagnosis();
        if (clinicalDiagnosis == null || clinicalDiagnosis.trim().isEmpty()) {
            errors.rejectValue("clinicalDiagnosis", "dissection.protocol.clinical.daignosis.empty.error");
        }
    }
}
