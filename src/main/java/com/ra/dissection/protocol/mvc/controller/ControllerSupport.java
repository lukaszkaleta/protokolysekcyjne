package com.ra.dissection.protocol.mvc.controller;

import com.ra.dissection.protocol.domain.common.Time;
import com.ra.dissection.protocol.domain.settings.DissectionProtocolCategory;
import com.ra.dissection.protocol.domain.settings.Doctor;
import com.ra.dissection.protocol.domain.settings.Hospital;
import com.ra.dissection.protocol.mvc.propertyeditors.TimePropertyEditor;
import com.ra.dissection.protocol.service.DoctorService;
import com.ra.dissection.protocol.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lukaszkaleta
 * @since 27.04.13 17:42
 */
@ControllerAdvice
public class ControllerSupport {

    @ModelAttribute(value = "datePattern")
    public String getDatePattern() {
        return "dd.MM.yyyy";
    }

    @ModelAttribute(value = "timePattern")
    public String getTimePattern() {
        return "HH:mm";
    }

    @ModelAttribute(value = "dissectionProtocolCategories")
    public List<DissectionProtocolCategory.Name> getDissectionProtocolCategories() {
        return Arrays.asList(DissectionProtocolCategory.Name.values());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, createMultiFormatDateEditor(getDatePattern(), "dd-MM-yyyy", "dd/MM/yyyy"));
        binder.registerCustomEditor(Time.class, new TimePropertyEditor());
    }

    private PropertyEditorSupport createMultiFormatDateEditor(String ... dateFormats) {
        List<CustomDateEditor> customDateEditors = new ArrayList<CustomDateEditor>();
        for (String format : dateFormats) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            customDateEditors.add(new CustomDateEditor(dateFormat, true));
        }
        return new MultiFormatDateEditor(customDateEditors);
    }
}
