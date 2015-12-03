package com.ra.dissection.protocol.mvc.controller.security;

import com.ra.dissection.protocol.mvc.controller.security.support.PasswordChangeModel;
import com.ra.dissection.protocol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author lukaszkaleta
 * @since 06.07.13 08:20
 */
@Controller
@RequestMapping("/account/user")
public class UserAccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    @Qualifier("passwordChangeValidator")
    private Validator passwordChangeValidator;

    @RequestMapping("/show")
    public ModelAndView showAccount(Principal principal) {
        return showAccountModelAndView(principal.getName());
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(PasswordChangeModel passwordChangeModel, BindingResult bindingResult) {
        passwordChangeValidator.validate(passwordChangeModel, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = accountView();
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        } else {
            accountService.changePassword(passwordChangeModel.getUsername(), passwordChangeModel.getNewPassword());
            ModelAndView modelAndView = showAccountModelAndView(passwordChangeModel.getUsername());
            modelAndView.addObject("passwordChanged", true);
            return modelAndView;
        }
    }

    private ModelAndView showAccountModelAndView(String username) {
        ModelAndView modelAndView = accountView();
        PasswordChangeModel passwordChangeModel = new PasswordChangeModel(username);
        modelAndView.addObject("passwordChangeModel", passwordChangeModel);
        return modelAndView;
    }

    private ModelAndView accountView() {
        return new ModelAndView("account/user");
    }
}
