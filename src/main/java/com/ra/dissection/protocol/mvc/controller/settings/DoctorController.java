package com.ra.dissection.protocol.mvc.controller.settings;

import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author lukaszkaleta
 * @since 25.04.13 11:20
 */
@Controller
@RequestMapping(value = "/settings/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    @Qualifier("doctorValidator")
    private Validator doctorValidator;

    @RequestMapping(value = "/show")
    public ModelAndView showAll() {
        return getDoctorListModelAndView();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(Doctor doctor, BindingResult bindingResult) {
        doctorValidator.validate(doctor, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView doctorFormModelAndView = getDoctorFormModelAndView(doctor);
            doctorFormModelAndView.addAllObjects(bindingResult.getModel());
            return doctorFormModelAndView;
        }
        if (doctor.getId() <= 0) {
            doctorService.create(doctor);
        } else {
            doctorService.update(doctor);
        }
        return showAll();
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable long id) {
        Doctor doctor = doctorService.read(id);
        return getDoctorFormModelAndView(doctor);
    }

    @RequestMapping(value = "/new")
    public ModelAndView startNew() {
        return getDoctorFormModelAndView(new Doctor());
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {
        doctorService.delete(id);
        return showAll();
    }

    private ModelAndView getDoctorFormModelAndView(Doctor doctor) {
        ModelAndView modelAndView = new ModelAndView("settings/doctor/doctor-form", "doctor", doctor);
        return modelAndView;
    }

    private ModelAndView getDoctorListModelAndView() {
        List<Doctor> doctors = doctorService.getAll();
        ModelAndView modelAndView = new ModelAndView("settings/doctor/doctor-list", "doctors", doctors);
        return modelAndView;
    }
}
