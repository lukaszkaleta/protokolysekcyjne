package com.ra.dissection.protocol.mvc.validation.security;

import com.ra.dissection.protocol.domain.security.UserAccount;
import com.ra.dissection.protocol.mvc.controller.security.support.PasswordChangeModel;
import com.ra.dissection.protocol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 06.07.13 09:47
 */
@Component("passwordChangeValidator")
public class PasswordChangeValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordChangeModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordChangeModel passwordChangeModel = (PasswordChangeModel) target;
        UserAccount userAccount = accountService.loadUserAccount(passwordChangeModel.getUsername());
        if (userAccount == null) {
            errors.rejectValue("username", "security.account.password.change.username.wrong");
            return;
        }
        String currentPersistPassword = userAccount.getPassword();
        String currentProvidedPassword = passwordChangeModel.getCurrentPassword();
        if (!currentPersistPassword.equals(currentProvidedPassword)) {
            errors.rejectValue("currentPassword", "security.account.password.change.currentPassword.wrong");
        }

        String newPassword = passwordChangeModel.getNewPassword();
        if (StringUtils.isEmpty(newPassword)) {
            errors.rejectValue("newPassword", "security.account.password.change.newPassword.empty");
        }
        String confirmPassword = passwordChangeModel.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "security.account.password.change.newPassword.not.repeated");
        }
        if (currentPersistPassword.equals(newPassword)) {
            errors.rejectValue("newPassword", "security.account.password.change.newPassword.not.changed");
        }
    }
}
