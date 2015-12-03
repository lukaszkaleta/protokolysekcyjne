package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.HistopathologicalExamination;
import com.ra.dissection.protocol.mvc.controller.ModelAttributeNames;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Badanie histopatologiczne
 *
 * @author lukaszkaleta
 * @since 25.04.13 12:38
 */
@Controller
@RequestMapping(value = "/protocol/histopathologicalExamination")
public class HistopathologicalExaminationController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    @Qualifier("histopathologicalExaminationValidator")
    private Validator histopathologicalExaminationValidator;

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        return new ModelAndView("protocol/histopathological-examination");
    }

    @RequestMapping(value = "/show/{dissectionProtocolId}")
    public ModelAndView show(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadHistopathologicalExamination(dissectionProtocolId);
        return histopathologicalFormModelAndView(dissectionProtocol, null);
    }

    @RequestMapping(value = "/show/{histopathologicalExaminationName}/{dissectionProtocolId}")
    public ModelAndView show(@PathVariable HistopathologicalExamination.Name histopathologicalExaminationName, @PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadHistopathologicalExamination(dissectionProtocolId);
        return histopathologicalFormModelAndView(dissectionProtocol, histopathologicalExaminationName);
    }

    @RequestMapping(value = "/nameChange", method = RequestMethod.POST)
    public ModelAndView nameChange(HistopathologicalExamination histopathologicalExamination) {
        return show(histopathologicalExamination.getName(), histopathologicalExamination.getDissectionProtocolId());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(HistopathologicalExamination histopathologicalExamination, BindingResult bindingResult) {

        histopathologicalExaminationValidator.validate(histopathologicalExamination, bindingResult);
        if (bindingResult.hasErrors()) {
            DissectionProtocol dissectionProtocol = new DissectionProtocol();
            dissectionProtocol.setHistopathologicalExaminations(Collections.singletonList(histopathologicalExamination));
            dissectionProtocol.setId(histopathologicalExamination.getDissectionProtocolId());
            ModelAndView modelAndView = histopathologicalFormModelAndView(dissectionProtocol, histopathologicalExamination.getName());
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        dissectionProtocolService.updateHistopatologicalExamination(histopathologicalExamination);
        ModelAndView show = show(histopathologicalExamination.getName(), histopathologicalExamination.getDissectionProtocolId());
        show.addObject(ModelAttributeNames.SUCCESS_MESSAGE_CODE, "dissection.protocol.histopathological.examination.save.success");
        return show;
    }

    @RequestMapping(value = "/delete/{dissectionProtocolId}/{histopathologicalExaminationId}")
    public ModelAndView delete(@PathVariable long dissectionProtocolId, @PathVariable long histopathologicalExaminationId) {
        dissectionProtocolService.deleteHistopathologicalExamination(histopathologicalExaminationId);
        ModelAndView modelAndView = show(dissectionProtocolId);
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE_CODE, "dissection.protocol.histopathological.examination.deleted");
        return modelAndView;
    }

    private ModelAndView histopathologicalFormModelAndView(DissectionProtocol dissectionProtocol, HistopathologicalExamination.Name name) {
        List<HistopathologicalExamination> histopathologicalExaminations = dissectionProtocol.getHistopathologicalExaminations();
        if (name == null) {
            if (histopathologicalExaminations.isEmpty()) {
                name = HistopathologicalExamination.Name.NORMAL;
            } else {
                name = histopathologicalExaminations.get(0).getName();
            }
        }

        HistopathologicalExamination selectedHistopathologicalExamination = null;
        for (HistopathologicalExamination histopathologicalExamination : histopathologicalExaminations) {
            if (name.equals(histopathologicalExamination.getName())) {
                selectedHistopathologicalExamination = histopathologicalExamination;
            }
        }
        if (selectedHistopathologicalExamination == null) {
            selectedHistopathologicalExamination = new HistopathologicalExamination();
            selectedHistopathologicalExamination.setDissectionProtocolId(dissectionProtocol.getId());
            selectedHistopathologicalExamination.setName(name);
        }

        ModelAndView modelAndView = new ModelAndView("protocol/histopathological-examination", "dissectionProtocol", dissectionProtocol);
        modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
        modelAndView.addObject("histopathologicalExamination", selectedHistopathologicalExamination);
        modelAndView.addObject("histopathologicalExaminationNames", Arrays.asList(HistopathologicalExamination.Name.values()));
        return modelAndView;
    }
}
