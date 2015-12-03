package com.ra.dissection.protocol.mvc.controller.search;

import com.ra.dissection.protocol.domain.common.Range;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.search.UserSearch;
import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.mvc.validation.UserSearchValidator;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.DoctorService;
import com.ra.dissection.protocol.service.HospitalService;
import com.ra.dissection.protocol.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 25.04.13 12:27, 6.8.3.0-R04v33
 */
public abstract class SearchCriteriaController {

    protected final String LATEST_VIEW = "/search/latest-list";
    protected final String RESULT_VIEW = "search/result-list";

    @Autowired
    protected UserSearchService userSearchService;

    @Autowired
    protected DissectionProtocolService dissectionProtocolService;

    @Autowired
    private UserSearchValidator userSearchValidator;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/reset")
    public ModelAndView reset(Principal principal) {
        userSearchService.reset(principal.getName());
        return getModelAndView(new UserSearch());
    }

    /**
     * New search criteria were provided.
     *
     * @param userSearch which user provide.
     *
     * @return
     */
    @RequestMapping(value = "/provide", method = RequestMethod.POST)
    public ModelAndView provide(UserSearch userSearch, BindingResult bindingResult, Principal principal) {
        if (bindingResult != null) {
            userSearchValidator.validate(userSearch, bindingResult);
        }
        if (bindingResult != null && bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(LATEST_VIEW, "userSearch", userSearch);
            modelAndView.addObject("latest", dissectionProtocolService.loadLatest(new Range<Integer>(0,100)));
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        } else {
            userSearch.setOwner(principal.getName());
            userSearch = userSearchService.accept(userSearch);
            ModelAndView modelAndView = new ModelAndView(RESULT_VIEW, "userSearch", userSearch);
            List<DissectionProtocol> search = dissectionProtocolService.search(userSearch);
            modelAndView.addObject("searchList", search);
            return modelAndView;
        }
    }

    protected abstract ModelAndView getModelAndView(UserSearch userSearch);

    @ModelAttribute(value = "hospitals")
    public List<Hospital> getHospitals() {
        return hospitalService.getAll();
    }

    @ModelAttribute(value = "doctors")
    public List<Doctor> getDoctors() {
        return doctorService.getAll();
    }

}
