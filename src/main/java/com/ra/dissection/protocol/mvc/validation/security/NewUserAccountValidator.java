package com.ra.dissection.protocol.mvc.validation.security;

import com.ra.dissection.protocol.domain.security.UserAccount;
import com.ra.dissection.protocol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author lukaszkaleta
 * @since 06.07.13 10:58
 */
@Component("newUserAccountValidator")
public class NewUserAccountValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccount.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserAccount userAccount = (UserAccount) target;
        String username = userAccount.getUsername();
        if (StringUtils.isEmpty(username)) {
            errors.rejectValue("username", "security.management.new.user.empty.username");
        }
        if (userAccount.getRoles().isEmpty()) {
            errors.rejectValue("roles", "security.management.new.user.no.roles");
        }
        UserAccount existingUserAccount = accountService.loadUserAccount(username);
        if (existingUserAccount != null) {
            errors.rejectValue("username", "security.management.new.user.duplicated");
        }
    }
}
