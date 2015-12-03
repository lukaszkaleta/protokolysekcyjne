package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 18.05.13 15:32
 */
@Component
@Qualifier("medicalPracticeAnalysisValidator")
public class MedicalPracticeAnalysisValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DissectionProtocol.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DissectionProtocol dissectionProtocol = (DissectionProtocol) target;
        String medicalPracticeAnalysis = dissectionProtocol.getMedicalPracticeAnalysis();
        if (medicalPracticeAnalysis == null || StringUtils.isEmpty(medicalPracticeAnalysis.trim())) {
            errors.rejectValue("medicalPracticeAnalysis", "medical.practice.analysis.empty.error");
        }
    }
}
