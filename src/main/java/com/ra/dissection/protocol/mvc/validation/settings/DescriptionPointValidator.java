package com.ra.dissection.protocol.mvc.validation.settings;

import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.mvc.controller.protocol.support.DescriptionForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 18.05.13 22:21
 */
@Component
@Qualifier("descriptionPointValidator")
public class DescriptionPointValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DescriptionPointSource.class.isAssignableFrom(clazz) || DescriptionPoint.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof DescriptionForm) {
            validateList((DescriptionForm) target, errors);
            return;
        }

        validateIndex("", target, errors);
    }

    private void validateIndex(String index, Object target, Errors errors) {
        String fieldName = null;
        String description = null;
        if (target instanceof DescriptionPoint) {
            fieldName = index + "descriptionPointSource.description";
            target = ((DescriptionPoint) target).getDescriptionPointSource();
        } else {
            fieldName = index + "description";
        }

        if (target instanceof DescriptionPointSource) {
            description = ((DescriptionPointSource) target).getDescription();
        }
        if (description == null || description.trim().isEmpty()) {
            errors.rejectValue(fieldName, "description.point.empty.error");
        }
    }

    private void validateList(DescriptionForm target, Errors errors) {
        for(int i = 0; i < target.getDescriptionPoints().size(); i++) {
            String index = "descriptionPoints[" + i + "].";
            validateIndex(index, target.getDescriptionPoints().get(i), errors);
        }
    }
}
