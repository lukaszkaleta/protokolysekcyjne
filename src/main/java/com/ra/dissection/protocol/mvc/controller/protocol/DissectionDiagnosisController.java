package com.ra.dissection.protocol.mvc.controller.protocol;

import com.google.common.collect.Multimap;
import com.ra.dissection.protocol.domain.common.OrderSwitch;
import com.ra.dissection.protocol.domain.protocol.DescriptionPoint;
import com.ra.dissection.protocol.domain.protocol.DissectionDiagnose;
import com.ra.dissection.protocol.domain.protocol.DissectionDiagnoseOption;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.settings.DescriptionPointSource;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource;
import com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSourceOption;
import com.ra.dissection.protocol.mvc.controller.ModelAttributeNames;
import com.ra.dissection.protocol.mvc.controller.protocol.support.DissectionDiagnoseModel;
import com.ra.dissection.protocol.mvc.controller.settings.DescriptionPointSourceController;
import com.ra.dissection.protocol.mvc.validation.protocol.DissectionDiagnoseOptionValidator;
import com.ra.dissection.protocol.mvc.validation.protocol.DissectionDiagnoseValidator;
import com.ra.dissection.protocol.service.DissectionDiagnoseSourceService;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 25.04.13 12:38
 */
@Controller
@RequestMapping(value = "/protocol/dissectionDiagnosis")
public class DissectionDiagnosisController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String VIEW = "protocol/dissection-diagnosis";
    private final String OPTION_VIEW = "protocol/dissection-diagnose-option";

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    private DissectionDiagnoseSourceService dissectionDiagnoseSourceService;

    @Autowired
    private DescriptionPointSourceController descriptionPointSourceController;

    @Autowired
    @Qualifier("dissectionDiagnoseValidator")
    private DissectionDiagnoseValidator dissectionDiagnoseValidator;

    @Autowired
    @Qualifier("dissectionDiagnoseOptionValidator")
    private DissectionDiagnoseOptionValidator dissectionDiagnoseOptionValidator;

    @RequestMapping("/denied")
    public ModelAndView denied() {
        return new ModelAndView(VIEW);
    }

    /**
     * Load dissection diagnosis for dissection protocol.
     * Creates new empty dissection diagnose which may be submitted.
     *
     * @param protocolId id of dissection protocol.
     *
     * @return model and view which contains dissection diagnose empty form and
     * list of dissection diagnoses for selected protocol
     */
    @RequestMapping(value = "/show/{protocolId}")
    public ModelAndView show(@PathVariable long protocolId) {
        DissectionDiagnose dissectionDiagnose = new DissectionDiagnose();
        dissectionDiagnose.setDissectionProtocolId(protocolId);
        DissectionDiagnoseModel dissectionDiagnoseModel = new DissectionDiagnoseModel(dissectionDiagnose);
        dissectionDiagnoseModel.setCreateSource(true);
        return dissectionDiagnoseModelAndView(protocolId, dissectionDiagnoseModel);
    }

    /**
     * Edit single dissection diagnose for protocol.
     *
     * @param dissectionDiagnoseId id of dissection diagnose.
     *
     * @return model and view which contain dissection diagnose form ready for editing
     * and list of dissection diagnosis for selected protocol.
     */
    @RequestMapping(value = "/edit/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public ModelAndView edit(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        DissectionDiagnose dissectionDiagnose = dissectionProtocolService.getDissectionDiagnose(dissectionDiagnoseId);
        ModelAndView modelAndView = dissectionDiagnoseModelAndView(dissectionProtocolId, new DissectionDiagnoseModel(dissectionDiagnose));
        modelAndView.addObject("editMode", true);
        return modelAndView;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(DissectionDiagnoseModel dissectionDiagnoseModel, BindingResult bindingResult) {
        DissectionDiagnose dissectionDiagnose = dissectionDiagnoseModel.getDissectionDiagnose();
        dissectionDiagnoseValidator.validate(dissectionDiagnose.getName(), bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = dissectionDiagnoseModelAndView(dissectionDiagnose.getDissectionProtocolId(), dissectionDiagnoseModel);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }
        if (dissectionDiagnoseModel.isCreateSource()) {
            // todo
        }
        dissectionProtocolService.updateDissectionDiagnose(dissectionDiagnose);
        return show(dissectionDiagnose.getDissectionProtocolId());
    }

    @RequestMapping("/delete/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public ModelAndView delete(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.deleteDissectionDiagnose(dissectionDiagnoseId);
        ModelAndView modelAndView = show(dissectionProtocolId);
        return modelAndView;
    }

    @RequestMapping("/missingPoint/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public String missingPoint(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        DissectionDiagnose dissectionDiagnose = dissectionProtocolService.loadSingleDissectionDiagnose(dissectionDiagnoseId);
        long dissectionDiagnoseSourceId = dissectionDiagnose.getDissectionDiagnoseSourceId();
        DissectionDiagnoseSource dissectionDiagnoseSource = dissectionDiagnoseSourceService.read(dissectionDiagnoseSourceId);
        long descriptionPointSourceId = dissectionDiagnoseSource.getDescriptionPointSourceId();
        return "redirect:/settings/descriptionPointSource/edit/" + String.valueOf(descriptionPointSourceId);
    }

    @RequestMapping("/{dissectionProtocolId}/{dissectionDiagnoseId}/space/add/below")
    public ModelAndView spaceAddBelow(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.addDissectionDiagnoseSpaceBelow(dissectionDiagnoseId);
        return show(dissectionProtocolId);
    }

    @RequestMapping("/{dissectionProtocolId}/{dissectionDiagnoseId}/space/remove/below")
    public ModelAndView spaceRemoveBelow(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.removeDissectionDiagnoseSpaceBelow(dissectionDiagnoseId);
        return show(dissectionProtocolId);
    }

    @RequestMapping("/{dissectionProtocolId}/{dissectionDiagnoseId}/space/add/above")
    public ModelAndView spaceAddAbove(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.addDissectionDiagnoseSpaceAbove(dissectionDiagnoseId);
        return show(dissectionProtocolId);
    }

    @RequestMapping("/{dissectionProtocolId}/{dissectionDiagnoseId}/space/remove/above")
    public ModelAndView spaceRemoveAbove(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.removeDissectionDiagnoseSpaceAbove(dissectionDiagnoseId);
        return show(dissectionProtocolId);
    }

    @Deprecated
    @RequestMapping("/down/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public ModelAndView down(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.reorderDissectionDiagnose(dissectionProtocolId, dissectionDiagnoseId, OrderSwitch.STEP_DOWN);
        return show(dissectionProtocolId);
    }

    @Deprecated
    @RequestMapping("/up/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public ModelAndView up(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.reorderDissectionDiagnose(dissectionProtocolId, dissectionDiagnoseId, OrderSwitch.STEP_UP);
        return show(dissectionProtocolId);
    }

    @Deprecated
    @RequestMapping("/fullDown/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public ModelAndView fullDown(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.reorderDissectionDiagnose(dissectionProtocolId, dissectionDiagnoseId, OrderSwitch.FULL_DOWN);
        return show(dissectionProtocolId);
    }

    @Deprecated
    @RequestMapping("/fullUp/{dissectionProtocolId}/{dissectionDiagnoseId}")
    public ModelAndView fullUp(@PathVariable long dissectionProtocolId, @PathVariable long dissectionDiagnoseId) {
        dissectionProtocolService.reorderDissectionDiagnose(dissectionProtocolId, dissectionDiagnoseId, OrderSwitch.FULL_UP);
        return show(dissectionProtocolId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(DissectionDiagnoseModel dissectionDiagnoseModel, BindingResult bindingResult) {
        DissectionDiagnose dissectionDiagnose = dissectionDiagnoseModel.getDissectionDiagnose();
        dissectionDiagnoseValidator.validate(dissectionDiagnose.getName(), bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = dissectionDiagnoseModelAndView(dissectionDiagnose.getDissectionProtocolId(), dissectionDiagnoseModel);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }

        ModelAndView modelAndView = null;
        if (dissectionDiagnoseModel.isCreateSource()) {
            // This means we are about to create new source of dissection diagnose
            // Newly created dissection diagnose will be added to protocol - but description can not be added.
            long newDescriptionPointSourceId = dissectionProtocolService.createNewDissectionDiagnose(dissectionDiagnose);
            modelAndView = show(dissectionDiagnose.getDissectionProtocolId());
            modelAndView.addObject("newDescriptionPointSourceId", newDescriptionPointSourceId);
        } else if (dissectionDiagnose.getDissectionDiagnoseSourceId() > 0) {
            // This means we are about to add new dissection diagnose
            DescriptionPointSource addedDescriptionPointSource = dissectionProtocolService.addDissectionDiagnose(dissectionDiagnose);
            modelAndView = show(dissectionDiagnose.getDissectionProtocolId());
            modelAndView.addObject("addedDescriptionPointSource", addedDescriptionPointSource);

            boolean optionAvailable = dissectionDiagnoseSourceService.isOptionAvailable(dissectionDiagnose.getDissectionDiagnoseSourceId());
            if (optionAvailable) {
                modelAndView.addObject("dissectionDiagnoseWithOptionId", dissectionDiagnose.getId());
            }

        } else {
            modelAndView = show(dissectionDiagnose.getDissectionProtocolId());
        }

        if (dissectionDiagnose.getDescriptionPointId() != null) {
            DescriptionPoint descriptionPoint = dissectionProtocolService.getDescriptionPoint(dissectionDiagnose.getDescriptionPointId());
            if (descriptionPoint != null) {
                modelAndView.addObject("addedDescriptionPoint", descriptionPoint);
            }
        }
        return modelAndView;
    }

    private ModelAndView dissectionDiagnoseModelAndView(long dissectionProtocolId, DissectionDiagnoseModel dissectionDiagnoseModel) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadDissectionDiagnosis(dissectionProtocolId);
        return dissectionDiagnoseModelAndView(dissectionProtocol, dissectionDiagnoseModel);
    }

    private ModelAndView dissectionDiagnoseModelAndView(DissectionProtocol dissectionProtocol, DissectionDiagnoseModel dissectionDiagnoseModel) {
        List<DissectionDiagnoseSource> all = dissectionDiagnoseSourceService.getAll();
        ModelAndView modelAndView = new ModelAndView(VIEW, ProtocolRequestCode.DISSECTION_PROTOCOL, dissectionProtocol);
        modelAndView.addObject("dissectionDiagnoseSources", all);
        modelAndView.addObject("dissectionDiagnoseModel", dissectionDiagnoseModel);
        if (dissectionProtocol != null) {
            modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
            boolean reorderEnabled = dissectionProtocol.getDissectionDiagnoseList().size() > 1;
            modelAndView.addObject("reorderEnabled", reorderEnabled);

            List<DissectionDiagnose> dissectionDiagnoseList = dissectionProtocol.getDissectionDiagnoseList();
            final Set<Long> dissectionDiagnoseIds = new HashSet<Long>();
            final Set<Long> dissectionDiagnoseSourceIds = new HashSet<Long>();
            for (DissectionDiagnose dissectionDiagnose : dissectionDiagnoseList) {
                dissectionDiagnoseIds.add(dissectionDiagnose.getId());
                dissectionDiagnoseSourceIds.add(dissectionDiagnose.getDissectionDiagnoseSourceId());
            }
            Map<Long,Boolean> optionSourceAvailable = dissectionDiagnoseSourceService.getOptionAvailableMap(dissectionDiagnoseSourceIds);
            modelAndView.addObject("optionSourceAvailable", optionSourceAvailable);

            Multimap<Long, DissectionDiagnoseOption> dissectionDiagnoseOptions = dissectionProtocolService.getDissectionDiagnoseOptions(dissectionDiagnoseIds);
            modelAndView.addObject("dissectionDiagnoseOptions", dissectionDiagnoseOptions.asMap());
        }

        return modelAndView;
    }

    //
    // Dissection Diagnose Options
    //

    @RequestMapping("/option/show/{dissectionDiagnoseId}")
    public ModelAndView showOptions(@PathVariable long dissectionDiagnoseId) {
        DissectionDiagnose dissectionDiagnose = dissectionProtocolService.getDissectionDiagnose(dissectionDiagnoseId);
        List<DissectionDiagnoseOption> dissectionDiagnoseOptions = dissectionProtocolService.getDissectionDiagnoseOptions(dissectionDiagnoseId);
        Map<Long, DissectionDiagnoseOption> dissectionDiagnoseOptionMap = new HashMap<Long, DissectionDiagnoseOption>();
        for (DissectionDiagnoseOption dissectionDiagnoseOption : dissectionDiagnoseOptions) {
            dissectionDiagnoseOptionMap.put(dissectionDiagnoseOption.getDissectionDiagnoseSourceOptionId(), dissectionDiagnoseOption);
        }
        long dissectionDiagnoseSourceId = dissectionDiagnose.getDissectionDiagnoseSourceId();
        List<DissectionDiagnoseSourceOption> options = dissectionDiagnoseSourceService.getOptions(dissectionDiagnoseSourceId);

        ModelAndView modelAndView = new ModelAndView(OPTION_VIEW, "dissectionDiagnose", dissectionDiagnose);
        modelAndView.addObject("dissectionDiagnoseOptions", dissectionDiagnoseOptionMap);
        modelAndView.addObject("dissectionDiagnoseSourceOptions", options);
        modelAndView.addObject("dissectionProtocolId", dissectionDiagnose.getDissectionProtocolId());
        modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionDiagnose.getDissectionProtocolId()));
        return modelAndView;
    }

    @RequestMapping("/option/add/{dissectionDiagnoseId}/{dissectionDiagnoseSourceOptionId}")
    public ModelAndView addOption(@PathVariable long dissectionDiagnoseId, @PathVariable long dissectionDiagnoseSourceOptionId) {
        dissectionProtocolService.addDissectionDiagnoseOption(dissectionDiagnoseId, dissectionDiagnoseSourceOptionId);
        ModelAndView modelAndView = showOptions(dissectionDiagnoseId);
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE_CODE, "protocol.dissection.diagnose.option.added");
        return modelAndView;
    }

    @RequestMapping(value = "/option/save", method = RequestMethod.POST)
    public ModelAndView saveOption(DissectionDiagnoseOption dissectionDiagnoseOption, BindingResult bindingResult) {
        dissectionDiagnoseOptionValidator.validate(dissectionDiagnoseOption.getName(), bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = showOptions(dissectionDiagnoseOption.getDissectionDiagnoseId());
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }
        dissectionProtocolService.updateDissectionDiagnoseOption(dissectionDiagnoseOption);
        ModelAndView modelAndView =  showOptions(dissectionDiagnoseOption.getDissectionDiagnoseId());
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE_CODE, "protocol.dissection.diagnose.option.saved");
        return modelAndView;
    }

    @RequestMapping("/option/delete/{dissectionDiagnoseId}/{dissectionDiagnoseOptionId}")
    public ModelAndView deleteOption(@PathVariable long dissectionDiagnoseId, @PathVariable long dissectionDiagnoseOptionId) {
        dissectionProtocolService.deleteDissectionDiagnoseOption(dissectionDiagnoseOptionId);
        ModelAndView modelAndView = showOptions(dissectionDiagnoseId);
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE_CODE, "protocol.dissection.diagnose.option.deleted");
        return modelAndView;
    }
}
