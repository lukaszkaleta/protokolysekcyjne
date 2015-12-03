package com.ra.dissection.protocol.mvc.validation.settings;

import com.ra.dissection.protocol.domain.settings.HospitalWard;
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
@Qualifier("hospitalWardValidator")
public class HospitalWardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return HospitalWard.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HospitalWard hospitalWard = (HospitalWard) target;
        if (hospitalWard.getHospitalId() <= 0) {
            errors.reject("settings.hospital.ward.no.hospital.relation");
        }
        if (StringUtils.isEmpty(hospitalWard.getName())) {
            errors.rejectValue("name", "settings.hospital.ward.no.name");
        }
    }
}
