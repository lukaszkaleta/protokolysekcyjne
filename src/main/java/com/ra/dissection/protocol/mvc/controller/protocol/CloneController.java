package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocolProgress;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lukaszkaleta
 * @since 11.06.13 06:16
 */
@Controller
@RequestMapping(value = "/protocol/clone")
public class CloneController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @Autowired
    private BasicDataController basicDataController;

    @RequestMapping("/start/{dissectionProtocolId}")
    public ModelAndView start(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadBasicData(dissectionProtocolId);
        DissectionProtocolProgress progress = dissectionProtocolService.getProgress(dissectionProtocolId);
        ModelAndView modelAndView = new ModelAndView("protocol/clone", "dissectionProtocol", dissectionProtocol);
        modelAndView.addObject("dissectionProtocolProgress", progress);
        return modelAndView;
    }

    @RequestMapping("/do/{dissectionProtocolId}")
    public ModelAndView doClone(@PathVariable long dissectionProtocolId) {
        long newId = dissectionProtocolService.clone(dissectionProtocolId);
        ModelAndView modelAndView = basicDataController.load(newId);
        modelAndView.addObject(BasicDataController.SuccessMessage.attributeName(), BasicDataController.SuccessMessage.CLONE_CREATED);
        return modelAndView;
    }
}
