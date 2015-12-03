package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 18.05.13 15:14
 */
@Component
@Qualifier("clinicalDataValidator")
public class ClinicalDataValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DissectionProtocol.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DissectionProtocol dissectionProtocol = (DissectionProtocol) target;
        String clinicalData = dissectionProtocol.getClinicalData();
        if (clinicalData == null || StringUtils.isEmpty(clinicalData.trim())) {
            errors.rejectValue("clinicalData", "clinical.data.empty.error");
        }
    }
}
