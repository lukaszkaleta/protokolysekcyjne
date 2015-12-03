package com.ra.dissection.protocol.mvc.controller.settings;

import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DescriptionPointType;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;
import com.ra.dissection.protocol.mvc.validation.settings.DescriptionPointValidator;
import com.ra.dissection.protocol.service.DescriptionPointSourceService;
import com.ra.dissection.protocol.service.DissectionDiagnoseSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 10.05.13 14:16
 */
@Controller
@RequestMapping(value = "/settings/descriptionPointSource")
public class DescriptionPointSourceController {

    @Autowired
    private DescriptionPointSourceService descriptionPointSourceService;

    @Autowired
    private DissectionDiagnoseSourceService dissectionDiagnoseSourceService;

    @Autowired
    @Qualifier("descriptionPointValidator")
    private DescriptionPointValidator descriptionPointValidator;

    @RequestMapping(value = "/start")
    public ModelAndView show() {
        return getListModelAndView(DissectionProtocolCategory.Name.ADULT);
    }

    @RequestMapping(value = "/filter/{categoryNameVariable}")
    public ModelAndView filter(@PathVariable String categoryNameVariable) {
        DissectionProtocolCategory.Name categoryName = DissectionProtocolCategory.Name.valueOf(categoryNameVariable.toUpperCase());
        return getListModelAndView(categoryName);
    }

    @RequestMapping(value = "/edit/{idVariable}")
    public ModelAndView edit(@PathVariable String idVariable) {
        long id = Long.parseLong(idVariable);
        DescriptionPointSource descriptionPointSource = descriptionPointSourceService.read(id);
        DissectionDiagnoseSource dissectionDiagnoseSource = dissectionDiagnoseSourceService.readByDescriptionPointSource(id);
        return getFormModelAndView(descriptionPointSource, dissectionDiagnoseSource);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(DescriptionPointSource descriptionPointSource, BindingResult bindingResult) {

        descriptionPointValidator.validate(descriptionPointSource, bindingResult);
        if (bindingResult.hasErrors()) {
            long id = descriptionPointSource.getId();
            DissectionDiagnoseSource dissectionDiagnoseSource = null;
            if (id > 0) {
                dissectionDiagnoseSource = dissectionDiagnoseSourceService.readByDescriptionPointSource(id);
            }
            ModelAndView formModelAndView = getFormModelAndView(descriptionPointSource, dissectionDiagnoseSource);
            formModelAndView.addAllObjects(bindingResult.getModel());
            return formModelAndView;
        }

        if (descriptionPointSource.getId() > 0) {
            descriptionPointSourceService.update(descriptionPointSource);
        } else {
            descriptionPointSourceService.create(descriptionPointSource);
        }
        switch (descriptionPointSource.getType()) {
            case GENERAL:
                return show();
            case DIAGNOSE:
                return "redirect:/settings/dissectionDiagnoseSource/start";
        }
        return null;
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable long id) {
        DescriptionPointSource descriptionPointSource = descriptionPointSourceService.delete(id);
        if (descriptionPointSource != null) {
            switch (descriptionPointSource.getType()) {
                case DIAGNOSE:
                    return "redirect:/settings/dissectionDiagnoseSource/start";
            }
        }
        return "redirect:/settings/descriptionPointSource/start";
    }

    @RequestMapping(value = "/new/{category}")
    public ModelAndView createNew(@PathVariable DissectionProtocolCategory.Name category) {
        int point = descriptionPointSourceService.getNextPoint(category);
        return createNewAt(category, point);
    }

    @RequestMapping(value = "/newAt/{category}/{point}")
    public ModelAndView createNewAt(@PathVariable DissectionProtocolCategory.Name category, @PathVariable int point) {
        DescriptionPointSource descriptionPointSource = new DescriptionPointSource();
        descriptionPointSource.setPoint(point);
        descriptionPointSource.setType(DescriptionPointType.GENERAL);
        descriptionPointSource.setPosition(descriptionPointSourceService.getNextPosition(point, category));
        return getFormModelAndView(descriptionPointSource, null);
    }

    private ModelAndView getFormModelAndView(DescriptionPointSource descriptionPointSource, DissectionDiagnoseSource dissectionDiagnoseSource) {
        ModelAndView modelAndView = new ModelAndView("settings/descriptionPointSource/dps-form");
        modelAndView.addObject(descriptionPointSource);
        if (dissectionDiagnoseSource != null) {
            modelAndView.addObject(dissectionDiagnoseSource);
        } else {
            List<DissectionDiagnoseSource> pointDissectionDiagnoseSources = descriptionPointSourceService.getDissectionDiagnoseWhichDescriptionReplacement(descriptionPointSource.getId());
            modelAndView.addObject("pointDissectionDiagnoseSources", pointDissectionDiagnoseSources);
        }
        return modelAndView;
    }

    private ModelAndView getListModelAndView(DissectionProtocolCategory.Name categoryName) {
        ModelAndView modelAndView = new ModelAndView("settings/descriptionPointSource/dps-list");
        Multimap<Integer,DescriptionPointSource> pointsForCategory = descriptionPointSourceService.getPointsForCategory(categoryName, DescriptionPointType.GENERAL);
        modelAndView.addObject("points", pointsForCategory.asMap());
        modelAndView.addObject("dissectionProtocolCategoryName", categoryName);
        return modelAndView;
    }
}
