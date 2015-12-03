package com.ra.dissection.protocol.mvc.controller.security;

import com.ra.dissection.protocol.domain.security.UserAccount;
import com.ra.dissection.protocol.domain.security.UserAuthority;
import com.ra.dissection.protocol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 06.07.13 08:20
 */
@Controller
@RequestMapping("/account/manager")
public class AccountManagerController {

    @Autowired
    private AccountService accountService;

    @Autowired
    @Qualifier("newUserAccountValidator")
    private Validator newUserAccountValidator;

    @RequestMapping("/show")
    public ModelAndView show() {
        return managerModelAndView();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addAccount(UserAccount newUserAccount, BindingResult bindingResult) {
        newUserAccountValidator.validate(newUserAccount, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = managerModelAndView();
            modelAndView.addObject(bindingResult.getModel());
            return modelAndView;
        } else {
            accountService.createUserAccount(newUserAccount.getUsername(), new HashSet<UserAuthority.Name>(newUserAccount.getRoles()));
            ModelAndView modelAndView = managerModelAndView();
            modelAndView.addObject("createdUserAccount", newUserAccount.getUsername());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView reset(UserAccount existingUserAccount) {
        accountService.changePassword(existingUserAccount.getUsername(), existingUserAccount.getUsername());
        ModelAndView modelAndView = managerModelAndView();
        modelAndView.addObject("resetedUserAccount", existingUserAccount.getUsername());
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(UserAccount existingUserAccount) {
        boolean deleted = accountService.deleteAccount(existingUserAccount.getUsername());
        ModelAndView modelAndView = managerModelAndView();
        if (deleted) {
            modelAndView.addObject("deletedUserAccount", existingUserAccount.getUsername());
        }
        return modelAndView;
    }

    private ModelAndView managerModelAndView() {
        ModelAndView modelAndView = managerView();
        modelAndView.addObject("userAccounts", accountService.loadAllAccounts());
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.addRole(UserAuthority.Name.USER);
        modelAndView.addObject("newUserAccount", newUserAccount);
        modelAndView.addObject("existingUserAccount", new UserAccount());
        modelAndView.addObject("roleNames", Arrays.asList(UserAuthority.Name.values()));
        return modelAndView;
    }

    private ModelAndView managerView() {
        return new ModelAndView("account/manager");
    }
}
