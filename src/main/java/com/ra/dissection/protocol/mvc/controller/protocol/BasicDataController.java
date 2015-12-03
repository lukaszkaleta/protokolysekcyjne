package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DeathStory;
import com.ra.dissection.protocol.domain.protocol.DeathStoryName;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.HospitalWardEntry;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;
import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.domain.settings.HospitalWard;
import com.ra.dissection.protocol.mvc.controller.protocol.support.BasicDataModel;
import com.ra.dissection.protocol.mvc.validation.protocol.BasicDataValidator;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.DoctorService;
import com.ra.dissection.protocol.service.HospitalService;
import com.ra.dissection.protocol.util.PeselValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author lukaszkaleta
 * @since 25.04.13 12:28
 */
@Controller
@RequestMapping(value = "/protocol/basic")
public class BasicDataController {

    public enum SuccessMessage {
        CREATED,
        SAVED,
        HOSPITAL_WARD_ADDED,
        CLONE_CREATED;

        public static String attributeName() {
            return "successMessage";
        }
    }

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private BasicDataValidator basicDataValidator;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/start")
    public ModelAndView start(Locale locale) {
        DissectionProtocol dissectionProtocol = new DissectionProtocol();
        String defaultAutopsyDoctorExecutorPresence = messageSource.getMessage("dissectionProtocol.basicData.autopsy.doctorExecutorPresence.default", new Object[] {}, locale);
        dissectionProtocol.getBasicData().getAutopsy().setDoctorExecutorPresence(defaultAutopsyDoctorExecutorPresence);

        return basicDataModelAndView(dissectionProtocol);
    }

    @RequestMapping(value = "/show/{protocolId}")
    public ModelAndView show(@PathVariable long protocolId) {
        return load(protocolId);
    }

    @RequestMapping(value = "/load")
    public ModelAndView load(@RequestParam long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadBasicData(dissectionProtocolId);
        return basicDataModelAndView(dissectionProtocol);
    }

    @RequestMapping(value = "/hospitalWardDelete/{dissectionProtocolId}/{hospitalWardId}")
    public ModelAndView deleteHospitalWard(@PathVariable long dissectionProtocolId, @PathVariable long hospitalWardId) {
        dissectionProtocolService.removeWard(dissectionProtocolId, hospitalWardId);
        return load(dissectionProtocolId);
    }

    @RequestMapping(value = "/delete/{dissectionProtocolId}")
    public String deleteDissectionProtocol(@PathVariable long dissectionProtocolId) {
        dissectionProtocolService.delete(dissectionProtocolId);
        return "redirect:/search/latest/start";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(BasicDataModel basicDataModel, BindingResult bindingResult) {
        basicDataValidator.validate(basicDataModel, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = basicDataModelAndView(basicDataModel);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }
        SuccessMessage successMessage = null;
        switch (basicDataModel.getSubmitMode()) {
            case DEATH_HOSPITAL_SELECT:
            case CATEGORY_SELECT:
                return basicDataModelAndView(basicDataModel);
            case HOSPITAL_WARD_ADD:
                BasicDataModel.NamedHospitalWard newHospitalWard = basicDataModel.getNewHospitalWard();
                String newHospitalWardName = newHospitalWard.getName();
                if (!StringUtils.isEmpty(newHospitalWardName)) {
                    Long hospitalId = basicDataModel.getDissectionProtocol().getBasicData().getDeathStory().getHospitalId();
                    HospitalWard hospitalWard = new HospitalWard();
                    hospitalWard.setHospitalId(hospitalId);
                    hospitalWard.setName(newHospitalWardName);
                    hospitalWard = hospitalService.createWardWithNameCheck(hospitalWard);
                    List<HospitalWardEntry> hospitalWardEntries = basicDataModel.getDissectionProtocol().getBasicData().getDeathStory().getHospitalWardEntries();
                    HospitalWardEntry hospitalWardEntry = new HospitalWardEntry();
                    hospitalWardEntry.setDissectionProtocolId(basicDataModel.getDissectionProtocol().getId());
                    hospitalWardEntry.setHospitalWardId(hospitalWard.getId());
                    hospitalWardEntries.add(hospitalWardEntry);
                    successMessage = SuccessMessage.HOSPITAL_WARD_ADDED;
                }
                // Now save the basic data
            case SAVE:
                DissectionProtocol dissectionProtocol = basicDataModel.prepareForSubmit();
                if (dissectionProtocol.getId() > 0) {
                    dissectionProtocolService.updateBasicData(dissectionProtocol);
                    successMessage = successMessage != null ? successMessage : SuccessMessage.SAVED;
                } else {
                    dissectionProtocol = dissectionProtocolService.create(dissectionProtocol);
                    successMessage = SuccessMessage.CREATED;
                }
                ModelAndView modelAndView = load(dissectionProtocol.getId());
                modelAndView.addObject(SuccessMessage.attributeName(), successMessage);
                return modelAndView;
        }
        return null;
    }

    @ModelAttribute("activeEntriesStore")
    public Map<DeathStoryName, Map<DissectionProtocolCategory.Name, Boolean>>  getDeathStoryName() {
        return DeathStoryName.activeNameMap();
    }

    private ModelAndView basicDataModelAndView(DissectionProtocol dissectionProtocol) {
        BasicDataModel basicDataModel = new BasicDataModel();
        return basicDataModelAndView(basicDataModel, dissectionProtocol);
    }

    private ModelAndView basicDataModelAndView(BasicDataModel basicDataModel) {
        DissectionProtocol dissectionProtocol = basicDataModel.prepareForSubmit();
        return basicDataModelAndView(dissectionProtocol);
    }

    private ModelAndView basicDataModelAndView(BasicDataModel basicDataModel, DissectionProtocol dissectionProtocol) {
        List<Hospital> hospitals = hospitalService.getAll();
        List<Hospital> dissectionHospitals = new ArrayList<Hospital>();
        Map<Long,Collection<HospitalWard>> allHospitalWards = hospitalService.getHospitalWards();
        for (Hospital hospital : hospitals) {
            Collection<HospitalWard> hospitalWards = allHospitalWards.get(hospital.getId());
            if (hospitalWards != null) {
                for (HospitalWard hospitalWard : hospitalWards) {
                    if (hospitalWard.isDissection()) {
                        dissectionHospitals.add(hospital);
                        break;
                    }
                }
            }
        }
        Map<Long, HospitalWard> possibleHospitalWards = new HashMap<Long, HospitalWard>();
        Long deathHospitalId = dissectionProtocol.getBasicData().getDeathStory().getHospitalId();
        if (deathHospitalId != null && deathHospitalId > 0) {
            Collection<HospitalWard> hospitalWards = allHospitalWards.get(deathHospitalId);
            for (HospitalWard hospitalWard : hospitalWards) {
                possibleHospitalWards.put(hospitalWard.getId(), hospitalWard);
            }
        }

        List<Doctor> allDoctors = doctorService.getAll();
        basicDataModel.prepareForView(dissectionProtocol, possibleHospitalWards);

        ModelAndView modelAndView = new ModelAndView("protocol/basic-data", "basic", basicDataModel);
        modelAndView.addObject("dissectionProtocolId", dissectionProtocol.getId());
        modelAndView.addObject("hospitals", hospitals);
        modelAndView.addObject("hospitalWardMap", allHospitalWards);
        modelAndView.addObject("dissectionHospitals", dissectionHospitals);
        modelAndView.addObject("doctors", allDoctors);
        modelAndView.addObject("possibleHospitalWardMap", possibleHospitalWards);
        modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
        modelAndView.addObject("deathStoryBookTypes", Arrays.asList(DeathStory.BookType.values()));
        String patientId = dissectionProtocol.getBasicData().getPatient().getIdentificationNumber();
        if (!StringUtils.isEmpty(patientId)) {
            PeselValidator peselValidator = new PeselValidator(patientId);
            boolean patientIdValid =peselValidator.isValid();
            modelAndView.addObject("patientIdValid", patientIdValid);
        }
        return modelAndView;

    }
}
