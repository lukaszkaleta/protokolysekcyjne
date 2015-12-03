package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * EPIKRYZA
 *
 * @author lukaszkaleta
 * @since 25.04.13 12:40
 */
@Controller
@RequestMapping(value = "/protocol/medicalPracticeAnalysis")
public class MedicalPracticeAnalysisController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    @Qualifier("medicalPracticeAnalysisValidator")
    private Validator medicalPracticeAnalysisValidator;

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        return new ModelAndView("protocol/medical-practice-analysis");
    }

    @RequestMapping(value = "/show/{dissectionProtocolId}")
    public ModelAndView show(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadMedicalPracticeAnalysis(dissectionProtocolId);
        return medicalPracticeAnalysisFormModelAndView(dissectionProtocol);
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(DissectionProtocol dissectionProtocol, BindingResult bindingResult) {

        medicalPracticeAnalysisValidator.validate(dissectionProtocol, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = medicalPracticeAnalysisFormModelAndView(dissectionProtocol);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        dissectionProtocolService.updateMedicalPracticeAnalysis(dissectionProtocol);
        ModelAndView show = show(dissectionProtocol.getId());
        show.addObject("saveSuccess", true);
        return show;
    }

    private ModelAndView medicalPracticeAnalysisFormModelAndView(DissectionProtocol dissectionProtocol) {
        ModelAndView modelAndView = new ModelAndView("protocol/medical-practice-analysis", "dissectionProtocol", dissectionProtocol);
        if (dissectionProtocol != null) {
            modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
        }
        return modelAndView;
    }
}
