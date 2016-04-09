package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;
import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DescriptionPointType;
import com.ra.dissection.protocol.mvc.controller.ModelAttributeNames;
import com.ra.dissection.protocol.mvc.controller.protocol.support.DescriptionForm;
import com.ra.dissection.protocol.mvc.validation.settings.DescriptionPointValidator;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.support.DescriptionPointSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Opis
 *
 * @author lukaszkaleta
 * @since 25.04.13 12:38
 */
@Controller
@RequestMapping(value = "/protocol/description")
public class DescriptionController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    private DescriptionPointSupport descriptionPointSupport;

    @Autowired
    @Qualifier("descriptionPointValidator")
    private DescriptionPointValidator descriptionPointValidator;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        return new ModelAndView("protocol/description");
    }

    @RequestMapping(value = "/show/{dissectionProtocolId}")
    public ModelAndView showForm(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadDescription(dissectionProtocolId);
        ModelAndView modelAndView = fullDescriptionModelAndView("protocol/description", dissectionProtocol);
        modelAndView.addObject("descriptionForm", new DescriptionForm(dissectionProtocol.getDescriptionPointList()));
        return modelAndView;
    }

    @RequestMapping(value = "/list/{dissectionProtocolId}")
    public ModelAndView showList(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadDescription(dissectionProtocolId);
        return fullDescriptionModelAndView("protocol/description-list", dissectionProtocol);
    }

    @RequestMapping(value = "/point/{descriptionPointId}")
    public ModelAndView showDescriptionPoint(@PathVariable long descriptionPointId) {
        DescriptionPoint descriptionPoint = dissectionProtocolService.getDescriptionPoint(descriptionPointId);
        return pointModelAndView(descriptionPoint);
    }

    @RequestMapping(value = "/save/{dissectionProtocolId}")
    public ModelAndView updateDescriptionPoints(@PathVariable long dissectionProtocolId, DescriptionForm descriptionForm, BindingResult bindingResult, Locale locale) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadDescription(dissectionProtocolId);
        List<DescriptionPoint> descriptionPoints = descriptionPointSupport.changes(dissectionProtocol.getDescriptionPointList(), descriptionForm.getDescriptionPoints());
        descriptionPointValidator.validate(descriptionForm, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = fullDescriptionModelAndView("protocol/description", dissectionProtocol);
            modelAndView.addObject("descriptionForm", descriptionForm);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }
        dissectionProtocolService.updateDescriptionPoints(descriptionPoints);
        return showForm(dissectionProtocolId);
    }

    @RequestMapping(value = "/update/{descriptionPointId}")
    public ModelAndView updateDescriptionPoint(@PathVariable long descriptionPointId, DescriptionPoint descriptionPoint, BindingResult bindingResult, Locale locale) {
        descriptionPointValidator.validate(descriptionPoint, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = pointModelAndView(descriptionPoint);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        dissectionProtocolService.updateDescriptionPoint(descriptionPoint);
        ModelAndView modelAndView = showForm(descriptionPoint.getDissectionProtocolId());
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE, messageSource.getMessage("protocol.description.point.saved", new Object[] {descriptionPoint.getPoint(), descriptionPoint.getDescriptionPointSource().getDigitPosition()}, locale));
        return modelAndView;
    }

    @RequestMapping(value = "/point/delete/{dissectionProtocolId}/{descriptionPointId}")
    public ModelAndView deleteDescriptionPoint(@PathVariable long dissectionProtocolId, @PathVariable long descriptionPointId, Locale locale) {
        DescriptionPoint descriptionPoint = dissectionProtocolService.deleteDescriptionPoint(descriptionPointId);
        ModelAndView modelAndView = showForm(dissectionProtocolId);
        if (descriptionPoint != null) {
            modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE, messageSource.getMessage("protocol.description.point.deleted", new Object[] {descriptionPoint.getPoint(), descriptionPoint.getDescriptionPointSource().getDigitPosition()}, locale));
        }
        return modelAndView;
    }

    @RequestMapping(value = "/add/{dissectionProtocolId}")
    public ModelAndView add(@PathVariable long dissectionProtocolId) {
        DescriptionPoint descriptionPoint = new DescriptionPoint();
        descriptionPoint.setDissectionProtocolId(dissectionProtocolId);
        DescriptionPointSource descriptionPointSource = new DescriptionPointSource();
        descriptionPointSource.setType(DescriptionPointType.CUSTOM);
        descriptionPoint.setDescriptionPointSource(descriptionPointSource);
        return pointModelAndView(descriptionPoint);
    }

    private ModelAndView fullDescriptionModelAndView(String view, DissectionProtocol dissectionProtocol) {
        List<DescriptionPoint> descriptionPointList = dissectionProtocol.getDescriptionPointList();
        Map<Integer, Collection<DescriptionPoint>> descriptionPointMap = descriptionPointSupport.pointingMap(descriptionPointList);
        ModelAndView modelAndView = new ModelAndView(view, "dissectionProtocol", dissectionProtocol);
        modelAndView.addObject("descriptionPointMap", descriptionPointMap);
        modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
        modelAndView.addObject("dissectionProtocolId", dissectionProtocol.getId());

        List<DissectionDiagnose> dissectionDiagnoseList = dissectionProtocol.getDissectionDiagnoseList();
        Map<Long, Boolean> descriptionPointIds = new HashMap<>();
        for(DissectionDiagnose dissectionDiagnose : dissectionDiagnoseList) {
            descriptionPointIds.put(dissectionDiagnose.getDescriptionPointId(), true);
        }
        modelAndView.addObject("changedDescriptionPointIds", descriptionPointIds);

        return modelAndView;

    }

    private ModelAndView pointModelAndView(DescriptionPoint descriptionPoint) {
        ModelAndView modelAndView = new ModelAndView("protocol/description-point", "descriptionPoint", descriptionPoint);
        if (descriptionPoint != null) {
            modelAndView.addObject("dissectionProtocolId", descriptionPoint.getDissectionProtocolId());
            modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(descriptionPoint.getDissectionProtocolId()));

            DissectionDiagnose pointDissectionDiagnose = dissectionProtocolService.loadClinicalDiagnoseForDescriptionPoint(descriptionPoint.getDissectionProtocolId(), descriptionPoint.getId());
            if (pointDissectionDiagnose != null) {
                modelAndView.addObject("pointDissectionDiagnose", pointDissectionDiagnose);
            }
        }
        return modelAndView;
    }
}
