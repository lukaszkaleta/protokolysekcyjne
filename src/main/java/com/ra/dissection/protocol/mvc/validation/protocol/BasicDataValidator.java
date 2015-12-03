package com.ra.dissection.protocol.mvc.validation.protocol;

import com.ra.dissection.protocol.mvc.controller.protocol.support.BasicDataModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 19.05.13 08:04
 */
@Component
@Qualifier("BasicDataValidator")
public class BasicDataValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BasicDataModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BasicDataModel basicDataModel = (BasicDataModel) target;
    }
}
