package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.mvc.controller.ModelAttributeNames;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Rozpoznanie kliniczne
 *
 * @author lukaszkaleta
 * @since 25.04.13 12:38
 */
@Controller
@RequestMapping(value = "/protocol/clinicalDiagnosis")
public class ClinicalDiagnosisController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    @Qualifier("clinicalDiagnosisValidator")
    private Validator clinicalDiagnosisValidator;

    @RequestMapping("/denied")
    public ModelAndView denied() {
        return clinicalDiagnosisModelAndView(null);
    }

    @RequestMapping(value = "/show/{protocolId}")
    public ModelAndView show(@PathVariable String protocolId) {
        if (StringUtils.isEmpty(protocolId)) {
            return clinicalDiagnosisModelAndView(new DissectionProtocol());
        } else {
            return load(Long.parseLong(protocolId));
        }
    }

    @RequestMapping(
            value="/reorder/{protocolId}",
            method= RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateOrder(@PathVariable String protocolId, @RequestBody List<String> ordered) {
        dissectionProtocolService.reorderDissectionDiagnose(Long.parseLong(protocolId), ordered);
        return null;
    }

    @RequestMapping(value = "/load")
    public ModelAndView load(@RequestParam long protocolId) {
        return clinicalDiagnosisModelAndView(dissectionProtocolService.loadClinicalDiagnosis(protocolId));
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(DissectionProtocol dissectionProtocol, BindingResult bindingResult) {
        clinicalDiagnosisValidator.validate(dissectionProtocol, bindingResult);
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = clinicalDiagnosisModelAndView(dissectionProtocol);
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        }
        dissectionProtocolService.updateClinicalDiagnosis(dissectionProtocol);
        ModelAndView modelAndView = clinicalDiagnosisModelAndView(dissectionProtocol);
        modelAndView.addObject(ModelAttributeNames.SUCCESS_MESSAGE_CODE, "dissection.protocol.clinical.diagnosis.save.success");
        return modelAndView;
    }

    private ModelAndView clinicalDiagnosisModelAndView(DissectionProtocol dissectionProtocol) {
        ModelAndView modelAndView = new ModelAndView("protocol/clinical-diagnosis", ProtocolRequestCode.DISSECTION_PROTOCOL, dissectionProtocol);
        if (dissectionProtocol != null) {
            modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocol.getId()));
        }
        return modelAndView;
    }
}
