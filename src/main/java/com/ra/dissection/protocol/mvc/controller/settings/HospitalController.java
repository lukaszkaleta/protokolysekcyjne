package com.ra.dissection.protocol.mvc.controller.settings;

import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.domain.settings.HospitalWard;
import com.ra.dissection.protocol.mvc.UploadItem;
import com.ra.dissection.protocol.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lukaszkaleta
 * @since 25.04.13 12:19, 6.8.3.0-R04v33
 */
@Controller
@RequestMapping(value = "/settings/hospital")
public class HospitalController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    @Qualifier("hospitalValidator")
    private Validator hospitalValidator;

    @Autowired
    @Qualifier("hospitalWardValidator")
    private Validator hospitalWardValidator;

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        return getHospitalListModelAndView();
    }

    @RequestMapping(value = "/new")
    public ModelAndView newHospital() {
        return getHospitalFormModelAndView();
    }

    @RequestMapping(value = "/ward/new/{hospitalId}")
    public ModelAndView newHospitalWard(@PathVariable long hospitalId) {
        HospitalWard hospitalWard = new HospitalWard();
        hospitalWard.setHospitalId(hospitalId);
        return getHospitalWardFormModelAndView(hospitalWard);
    }

    @RequestMapping(value = "/edit/{hospitalId}")
    public ModelAndView startEdit(@PathVariable long hospitalId) {
        return getHospitalFormModelAndView(hospitalService.read(hospitalId));
    }

    @RequestMapping(value = "/ward/edit/{hospitalWardId}")
    public ModelAndView startWardEdit(@PathVariable long hospitalWardId) {
        HospitalWard hospitalWard = hospitalService.readHospitalWard(hospitalWardId);
        return getHospitalWardFormModelAndView(hospitalWard);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(Hospital hospital, BindingResult bindingResult) {
        hospitalValidator.validate(hospital, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView hospitalErrorModelAndView = getHospitalFormModelAndView(hospital);
            hospitalErrorModelAndView.addAllObjects(bindingResult.getModel());
            return hospitalErrorModelAndView;
        }
        if (hospital.getId() <= 0) {
            hospitalService.create(hospital);
        } else {
            hospitalService.update(hospital);
        }
        return show();
    }

    @RequestMapping(value = "/ward/save", method = RequestMethod.POST)
    public ModelAndView save(HospitalWard hospitalWard, BindingResult bindingResult) {
        hospitalWardValidator.validate(hospitalWard, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView hospitalErrorModelAndView = getHospitalWardFormModelAndView(hospitalWard);
            hospitalErrorModelAndView.addAllObjects(bindingResult.getModel());
            return hospitalErrorModelAndView;
        }
        if (hospitalWard.getId() <= 0) {
            hospitalService.createWard(hospitalWard);
        } else {
            hospitalService.updateWard(hospitalWard);
        }
        return show();
    }

    @RequestMapping(value = "/ward/delete/{hospitalWardId}")
    public ModelAndView delete(@PathVariable long hospitalWardId) {
        hospitalService.deleteWard(hospitalWardId);
        return show();
    }

    @RequestMapping(value = "/addImage", method = RequestMethod.POST)
    public ModelAndView addHospitalImage(UploadItem uploadItem) {
        long hospitalId = uploadItem.getId();
        byte[] hospitalImage = uploadItem.getFile().getBytes();
        hospitalService.updateHospitalImage(hospitalId, hospitalImage);
        return show();
    }

    @RequestMapping(value = "/ward/addImage", method = RequestMethod.POST)
    public ModelAndView addHospitalWardImage(UploadItem uploadItem) {
        long hospitalWardId = uploadItem.getId();
        byte[] hospitalWardImage = uploadItem.getFile().getBytes();
        hospitalService.updateHospitalWardImage(hospitalWardId, hospitalWardImage);
        return show();
    }

    @RequestMapping(value = "/image/{hospitalId}")
    public void image(@PathVariable long hospitalId, HttpServletResponse response) {
        InputStream hospitalImageStream = hospitalService.getHospitalImage(hospitalId);
        response.setHeader("Content-Disposition", "attachment; filename=image.jpeg");
        try {
            FileCopyUtils.copy(hospitalImageStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("Error while copying hospital image: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/ward/image/{hospitalWardId}")
    public void wardImage(@PathVariable long hospitalWardId, HttpServletResponse response) {
        InputStream hospitalWardImageStream = hospitalService.getHospitalWardImage(hospitalWardId);
        response.setHeader("Content-Disposition", "attachment; filename=image.jpeg");
        try {
            FileCopyUtils.copy(hospitalWardImageStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("Error while copying hospital ward image: " + e.getMessage(), e);
        }
    }

    private ModelAndView getHospitalListModelAndView() {
        ModelAndView modelAndView = new ModelAndView("settings/hospital/hospital-list");
        List<Hospital> all = hospitalService.getAll();
        modelAndView.addObject("hospitals", all);
        Map<Long, Collection<HospitalWard>> hospitalWards = hospitalService.getHospitalWards();
        modelAndView.addObject("hospitalWards", hospitalWards);
        return modelAndView;
    }

    private ModelAndView getHospitalFormModelAndView() {
        return getHospitalFormModelAndView(new Hospital());
    }

    private ModelAndView getHospitalFormModelAndView(Hospital hospital) {
        return new ModelAndView("settings/hospital/hospital-form", "hospital", hospital);
    }

    private ModelAndView getHospitalWardFormModelAndView(HospitalWard hospitalWard) {
        Hospital hospital = hospitalService.read(hospitalWard.getHospitalId());
        ModelAndView hospitalWardModelAndView = new ModelAndView("settings/hospital/hospital-ward-form", "hospitalWard", hospitalWard);
        hospitalWardModelAndView.addObject("hospital", hospital);
        return hospitalWardModelAndView;
    }

    @ModelAttribute("uploadItem")
    public UploadItem getUploadItem() {
        return new UploadItem();
    }
}
