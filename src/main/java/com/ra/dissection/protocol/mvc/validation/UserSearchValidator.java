package com.ra.dissection.protocol.mvc.validation;

import com.ra.dissection.protocol.domain.search.UserSearch;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * @author lukaszkaleta
 * @since 27.04.13 12:57
 */
@Component
public class UserSearchValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSearch.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserSearch userSearch = (UserSearch) target;

        if (userSearch.getHospitalId() != null && userSearch.getHospitalId() > 0) {
            return;
        }

        if (userSearch.getDoctorId() != null && userSearch.getDoctorId() > 0) {
            return;
        }

        if (userSearch.getMedicalExaminationDate() != null) {
            return;
        }

        if (userSearch.getMedicalExaminationDateFrom() != null) {
            return;
        }

        if (userSearch.getMedicalExaminationDateTo() != null) {
            return;
        }

        Integer medicalExaminationTimeFromValue = userSearch.getMedicalExaminationTimeFromValue();
        if (medicalExaminationTimeFromValue != null && medicalExaminationTimeFromValue > 0) {
            return;
        }

        Integer medicalExaminationTimeToValue = userSearch.getMedicalExaminationTimeToValue();
        if (medicalExaminationTimeToValue != null && medicalExaminationTimeToValue > 0) {
            return;
        }

        if (userSearch.getMedicalExaminationNumber() != null && !userSearch.getMedicalExaminationNumber().isEmpty()) {
            return;
        }

        if (userSearch.getPatientLastName() != null && !userSearch.getPatientLastName().isEmpty()) {
            return;
        }

        if (userSearch.getPatientFirstName() != null && !userSearch.getPatientFirstName().isEmpty()) {
            return;
        }

        if (userSearch.getPatientIdentificationNumber() != null && !userSearch.getPatientIdentificationNumber().isEmpty()) {
            return;
        }

        errors.reject("user.search.no.criteria.provided");
    }
}
