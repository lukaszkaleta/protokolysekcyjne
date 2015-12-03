package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.domain.protocol.DissectionProtocol;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocolProgress;
import com.ra.dissection.protocol.domain.protocol.DissectionProtocolStatus;
import com.ra.dissection.protocol.service.DissectionProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lukaszkaleta
 * @since 05.07.13 19:59
 */
@Controller
@RequestMapping("/protocol/status")
public class StatusController {

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @RequestMapping("/show/{dissectionProtocolId}")
    public ModelAndView show(@PathVariable long dissectionProtocolId) {
        DissectionProtocol dissectionProtocol = dissectionProtocolService.loadStatus(dissectionProtocolId);
        ModelAndView modelAndView = new ModelAndView("protocol/status-change", ProtocolRequestCode.DISSECTION_PROTOCOL, dissectionProtocol);
        DissectionProtocolProgress progress = dissectionProtocolService.getProgress(dissectionProtocolId);
        modelAndView.addObject(ProtocolRequestCode.DP_PROGRESS, progress);
        return modelAndView;
    }

    @RequestMapping("/change/{dissectionProtocolId}/{status}")
    public ModelAndView change(@PathVariable long dissectionProtocolId, @PathVariable DissectionProtocolStatus status) {
        dissectionProtocolService.updateStatus(dissectionProtocolId, status);
        ModelAndView modelAndView = show(dissectionProtocolId);
        modelAndView.addObject("success", true);
        return modelAndView;
    }
}
