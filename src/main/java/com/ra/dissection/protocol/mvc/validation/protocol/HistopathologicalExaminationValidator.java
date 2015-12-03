package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.HistopathologicalExamination;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 18.05.13 14:15
 */
@Component
@Qualifier("histopathologicalExaminationValidator")
public class HistopathologicalExaminationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return HistopathologicalExamination.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HistopathologicalExamination dissectionProtocol = (HistopathologicalExamination) target;
        String histopathologicalExamination = dissectionProtocol.getDescription();
        if (histopathologicalExamination == null || StringUtils.isEmpty(histopathologicalExamination.trim())) {
            errors.rejectValue("histopathologicalExamination", "histopathological.examination.empty.error");
        }
    }
}
