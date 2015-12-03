package com.ra.dissection.protocol.mvc.validation.settings;

import com.ra.dissection.protocol.domain.settings.Doctor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 18.05.13 21:27
 */
@Component
@Qualifier("doctorValidator")
public class DoctorValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Doctor.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Doctor doctor = (Doctor) target;
        String firstName = doctor.getFirstName();
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.rejectValue("firstName", "settings.doctor.firstName.empty.error");
        }
        String lastName = doctor.getLastName();
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.rejectValue("lastName", "settings.doctor.lastName.empty.error");
        }
    }
}
