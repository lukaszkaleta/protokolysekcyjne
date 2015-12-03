package com.ra.dissection.protocol.mvc.controller.search;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.search.UserSearch;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 27.04.13 17:38
 */
@Controller
@RequestMapping(value = "/search/result")
public class SearchResultController extends SearchCriteriaController {

    @RequestMapping(value = "/start")
    public ModelAndView start(Principal principal) {
        UserSearch current = userSearchService.current(principal.getName());
        return provide(current, null, principal);
    }

    @Override
    protected ModelAndView getModelAndView(UserSearch userSearch) {
        return new ModelAndView("search/result-list", "userSearch", userSearch);
    }
}
