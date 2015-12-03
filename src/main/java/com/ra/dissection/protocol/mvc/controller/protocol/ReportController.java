package com.ra.dissection.protocol.mvc.controller.protocol;

import com.ra.dissection.protocol.service.DissectionProtocolService;
import com.ra.dissection.protocol.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author lukaszkaleta
 * @since 13.05.13 22:57
 */
@Controller
@RequestMapping(value = "/protocol/report")
public class ReportController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportService reportService;

    @Autowired
    private DissectionProtocolService dissectionProtocolService;

    @RequestMapping(value = "/preview/{dissectionProtocolId}")
    public ModelAndView report(@PathVariable long dissectionProtocolId) {
        ModelAndView modelAndView = new ModelAndView("protocol/print-preview");
        modelAndView.addObject("dissectionProtocolId", dissectionProtocolId);
        modelAndView.addObject("dissectionProtocolProgress", dissectionProtocolService.getProgress(dissectionProtocolId));
        String reportName;
        try {
            String niceName = reportService.getReportName(dissectionProtocolId);
            reportName = URLEncoder.encode(niceName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            reportName = "unknonw";
        }
        modelAndView.addObject("dissectionProtocolName", reportName);
        return modelAndView;
    }

    @RequestMapping(value = "/{dissectionProtocolId}/{name}.pdf")
    public void pdf(@PathVariable long dissectionProtocolId, HttpServletResponse response) {
        reportService.generateIfNeeded(dissectionProtocolId);
        InputStream fullReport = reportService.getFullReport(dissectionProtocolId);
        //response.setHeader("Content-Disposition", "attachment; filename=protokol.pdf");
        try {
            FileCopyUtils.copy(fullReport, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("Error while copying report: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/generate/{dissectionProtocolId}")
    public ModelAndView generate(@PathVariable long dissectionProtocolId, HttpServletResponse response) {
        reportService.generate(dissectionProtocolId);
        return report(dissectionProtocolId);
    }
}
