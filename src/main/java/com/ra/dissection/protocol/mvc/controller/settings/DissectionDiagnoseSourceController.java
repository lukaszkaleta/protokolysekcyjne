package com.ra.dissection.protocol.mvc.controller.settings;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.settings.*;
import com.ra.dissection.protocol.mvc.controller.ModelAttributeNames;
import com.ra.dissection.protocol.mvc.controller.settings.support.DissectionDiagnoseSourceFilter;
import com.ra.dissection.protocol.service.DissectionDiagnoseSourceService;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.ra.dissection.protocol.mvc.controller.ModelAttributeNames.*;

/**
 * @author lukaszkaleta
 * @since 11.05.13 08:25
 */
@Controller
@RequestMapping(value = "/settings/dissectionDiagnoseSource")
public class DissectionDiagnoseSourceController {

    @Autowired
    private DissectionDiagnoseSourceService dissectionDiagnoseSourceService;

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    @Qualifier("dissectionDiagnoseSourceValidator")
    private Validator dissectionDiagnoseSourceValidator;

    @Autowired
    @Qualifier("dissectionDiagnoseSourceOptionValidator")
    private Validator dissectionDiagnoseSourceOptionValidator;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/start")
    public ModelAndView show() {
        List<DissectionDiagnoseSource> all = dissectionDiagnoseSourceService.getAll();
        return listModelAndView(new DissectionDiagnoseSourceFilter(), all);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ModelAndView filter(DissectionDiagnoseSourceFilter filter) {
        DissectionProtocolCategory dissectionProtocolCategory = filter.getDissectionProtocolCategory();
        String letter = filter.getLetter();
        List<DissectionDiagnoseSource> all = dissectionDiagnoseSourceService.getAll(dissectionProtocolCategory, letter);
        return listModelAndView(filter, all);
    }

    @RequestMapping(value = "/new")
    public ModelAndView newDissectionDiagnoseSource() {
        return formModelAndView(new DissectionDiagnoseSource());
    }

    @RequestMapping(value = "/edit/{dissectionDiagnoseSourceId}")
    public ModelAndView editDissectionDiagnose(@PathVariable long dissectionDiagnoseSourceId) {
        DissectionDiagnoseSource dissectionDiagnoseSource = dissectionDiagnoseSourceService.read(dissectionDiagnoseSourceId);
        return formModelAndView(dissectionDiagnoseSource);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(DissectionDiagnoseSource dissectionDiagnoseSource, BindingResult bindingResult) {
        DissectionDiagnoseName name = dissectionDiagnoseSource.getName();
        dissectionDiagnoseSourceValidator.validate(name, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = formModelAndView(dissectionDiagnoseSource);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        // Trim spaces so sorting is not broken.
        name.setLatin(name.getLatin().trim());
        name.setTranslated(name.getTranslated().trim());

        long id = dissectionDiagnoseSource.getId();
        if (id <= 0) {
            dissectionDiagnoseSourceService.create(dissectionDiagnoseSource);
            id = dissectionDiagnoseSource.getId();
            return "forward:/settings/descriptionPointSource/edit/" + dissectionDiagnoseSource.getDescriptionPointSourceId();
        } else {
            dissectionDiagnoseSourceService.update(dissectionDiagnoseSource);
            return show();
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {
        dissectionDiagnoseSourceService.delete(id);
        return show();
    }

    @ModelAttribute("dissectionDiagnoseTypes")
    public List<DissectionDiagnoseType> getDissectionDiagnoseTypes() {
        return Arrays.asList(DissectionDiagnoseType.values());
    }

    private ModelAndView formModelAndView(DissectionDiagnoseSource dissectionDiagnoseSource) {
        List<DissectionDiagnoseSource> all = dissectionDiagnoseSourceService.getAll();

        ModelAndView modelAndView = new ModelAndView("settings/dissectionDiagnoseSource/dds-form");
        if (dissectionDiagnoseSource.isDissectionDiagnoseSourceOptionAvailable()) {
            List<DissectionDiagnoseSourceOption> options = dissectionDiagnoseSourceService.getOptions(dissectionDiagnoseSource.getId());
            modelAndView.addObject(DISSECTION_DIAGNOSE_SOURCE_OPTIONS, options);
        } else {
            modelAndView.addObject(DISSECTION_DIAGNOSE_SOURCE_OPTIONS, Collections.emptyList());
        }

        List<DissectionProtocol> dissectionDiagnoseProtocols = dissectionProtocolService.getDissectionDiagnoseProtocols(dissectionDiagnoseSource.getId());

        modelAndView.addObject("dissectionDiagnoseSource", dissectionDiagnoseSource);
        modelAndView.addObject("dissectionDiagnoseProtocols", dissectionDiagnoseProtocols);
        modelAndView.addObject("dissectionDiagnoseSources", all);
        return modelAndView;
    }

    private ModelAndView listModelAndView(DissectionDiagnoseSourceFilter filter, List<DissectionDiagnoseSource> list) {
        ModelAndView modelAndView = new ModelAndView("settings/dissectionDiagnoseSource/dds-list");
        modelAndView.addObject("dissectionDiagnoseSourceFilter", filter);
        modelAndView.addObject("dissectionDiagnoseSources", list);

        Set<Long> dissectionDiagnoseSourceIds = new HashSet<Long>(Collections2.transform(list, new Function<DissectionDiagnoseSource, Long>() {
            @Override
            public Long apply(DissectionDiagnoseSource input) {
                return input.isDissectionDiagnoseSourceOptionAvailable() ? input.getId() : 0l;
            }
        }));
        dissectionDiagnoseSourceIds.remove(0l);
        if (dissectionDiagnoseSourceIds.isEmpty()) {
            modelAndView.addObject(DISSECTION_DIAGNOSE_SOURCE_OPTIONS, Collections.emptyMap());
        } else {
            Multimap<Long,DissectionDiagnoseSourceOption> options = dissectionDiagnoseSourceService.getOptions(dissectionDiagnoseSourceIds);
            modelAndView.addObject(DISSECTION_DIAGNOSE_SOURCE_OPTIONS, options.asMap());
        }

        modelAndView.addObject("letters", dissectionDiagnoseSourceService.getLatinLetters());
        return modelAndView;
    }

    //
    // DissectionDiagnoseSourceOption
    //

    /**
     * Request which is called when one start to create new dissection diagnose source option.
     *
     * @param dissectionDiagnoseSourceId id of dissection diagnose source, for which new
     *                                   dissection diagnose source options will be created.
     *
     * @return Model and view which contains form for creating new dissection diagnose source option.
     */
    @RequestMapping(value = "/option/new/{dissectionDiagnoseSourceId}")
    public ModelAndView newOption(@PathVariable long dissectionDiagnoseSourceId) {
        DissectionDiagnoseSourceOption option = new DissectionDiagnoseSourceOption();
        option.setDissectionDiagnoseSourceId(dissectionDiagnoseSourceId);
        return dissectionDiagnoseSourceOptionFormView(option);
    }

    /**
     * Request which is called when one start to edit existing dissection diagnose source option.
     *
     * @param dissectionDiagnoseSourceId if of dissection diagnose source to which belongs existing option.
     * @param dissectionDiagnoseSourceOptionId id of dissection diagnose source option which will be edited.
     *
     * @return Model and view which contains form for editing existing dissection diagnose source option.
     */
    @RequestMapping(value = "/option/edit/{dissectionDiagnoseSourceId}/{dissectionDiagnoseSourceOptionId}")
    public ModelAndView editOption(@PathVariable long dissectionDiagnoseSourceId, @PathVariable long dissectionDiagnoseSourceOptionId) {
        DissectionDiagnoseSourceOption option = dissectionDiagnoseSourceService.readOption(dissectionDiagnoseSourceOptionId);
        return dissectionDiagnoseSourceOptionFormView(option);
    }

    /**
     *
     * @param dissectionDiagnoseSourceOption
     * @param bindingResult
     * @param locale
     * @return
     */
    @RequestMapping(value = "/option/save", method = RequestMethod.POST)
    public ModelAndView saveOption(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption, BindingResult bindingResult, Locale locale) {
        dissectionDiagnoseSourceOptionValidator.validate(dissectionDiagnoseSourceOption.getName(), bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = dissectionDiagnoseSourceOptionFormView(dissectionDiagnoseSourceOption);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        long id = dissectionDiagnoseSourceOption.getId();
        String successMessage;
        if (id > 0) {
            dissectionDiagnoseSourceService.updateOption(dissectionDiagnoseSourceOption);
            successMessage = messageSource.getMessage("", null, locale);
        } else {
            dissectionDiagnoseSourceService.createOption(dissectionDiagnoseSourceOption);
            successMessage = messageSource.getMessage("", null, locale);
        }
        ModelAndView modelAndView = editDissectionDiagnose(dissectionDiagnoseSourceOption.getDissectionDiagnoseSourceId());
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE, successMessage);
        return modelAndView;
    }

    @RequestMapping(value = "/option/delete/{dissectionDiagnoseSourceId}/{dissectionDiagnoseSourceOptionId}")
    public ModelAndView deleteOption(@PathVariable long dissectionDiagnoseSourceId, @PathVariable long dissectionDiagnoseSourceOptionId, Locale locale) {
        dissectionDiagnoseSourceService.deleteOption(dissectionDiagnoseSourceOptionId);
        ModelAndView modelAndView = editDissectionDiagnose(dissectionDiagnoseSourceId);
        String successMessage = messageSource.getMessage("", null, locale);
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE, successMessage);
        return modelAndView;
    }

    private ModelAndView dissectionDiagnoseSourceOptionFormView(DissectionDiagnoseSourceOption dissectionDiagnoseSourceOption) {
        long dissectionDiagnoseSourceId = dissectionDiagnoseSourceOption.getDissectionDiagnoseSourceId();
        DissectionDiagnoseSource dissectionDiagnoseSource = dissectionDiagnoseSourceService.read(dissectionDiagnoseSourceId);
        ModelAndView modelAndView = new ModelAndView("settings/dissectionDiagnoseSource/dds-option-form");
        modelAndView.addObject(dissectionDiagnoseSource);
        modelAndView.addObject(dissectionDiagnoseSourceOption);
        return modelAndView;
    }
}
