package com.ra.dissection.protocol.mvc.validation.settings;

import com.ra.dissection.protocol.domain.settings.Hospital;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 15.05.13 22:44
 */
@Component
@Qualifier("hospitalValidator")
public class HospitalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Hospital.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Hospital hospital = (Hospital) target;
        if (StringUtils.isEmpty(hospital.getName())) {
            errors.rejectValue("name", "settings.hospital.no.name");
        }
    }
}

