package com.ra.dissection.protocol.mvc.controller.search;

import com.ra.dissection.protocol.domain.common.Range;
import com.ra.dissection.protocol.domain.search.UserSearch;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author lukaszkaleta
 * @since 27.04.13 19:51
 */
@Controller
@RequestMapping(value = "/search/latest")
public class BrowseLatestController extends SearchCriteriaController {

    @RequestMapping(value = "/start")
    public ModelAndView start(Principal principal) {
        UserSearch current = userSearchService.current(principal.getName());
        return getModelAndView(current);
    }

    @Override
    protected ModelAndView getModelAndView(UserSearch userSearch) {
        ModelAndView modelAndView = new ModelAndView("/search/latest-list", "userSearch", userSearch);
        modelAndView.addObject("latest", dissectionProtocolService.loadLatest(new Range<Integer>(0,100)));
        return modelAndView;
    }
}
