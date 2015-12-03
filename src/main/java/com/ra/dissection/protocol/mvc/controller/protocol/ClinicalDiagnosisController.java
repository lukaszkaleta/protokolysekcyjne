package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.mvc.controller.ModelAttributeNames;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
