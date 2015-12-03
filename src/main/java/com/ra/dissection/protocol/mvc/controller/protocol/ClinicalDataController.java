package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Wazne dane kliniczne
 *
 * @author lukaszkaleta
 * @since 25.04.13 12:38
 */
@Controller
@RequestMapping(value = "/protocol/clinicalData")
public class ClinicalDataController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    @Qualifier("clinicalDataValidator")
    private Validator clinicalDataValidator;

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        return new ModelAndView("protocol/clinical-data");
    }

    @RequestMapping(value = "/show/{dissectionProtocolId}")
    public ModelAndView show(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadClinicalData(dissectionProtocolId);
        return clinicalDataFormModelAndView(dissectionProtocol);
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(DissectionProtocol dissectionProtocol, BindingResult bindingResult) {

        clinicalDataValidator.validate(dissectionProtocol, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = clinicalDataFormModelAndView(dissectionProtocol);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        dissectionProtocolService.updateClinicalData(dissectionProtocol);
        ModelAndView show = show(dissectionProtocol.getId());
        show.addObject("saveSuccess", true);
        return show;
    }

    private ModelAndView clinicalDataFormModelAndView(DissectionProtocol dissectionProtocol) {
        ModelAndView modelAndView = new ModelAndView("protocol/clinical-data", "dissectionProtocol", dissectionProtocol);
        modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
        return modelAndView;
    }
}
