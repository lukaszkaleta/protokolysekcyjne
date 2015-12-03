package com.ra.dissection.protocol.mvc.controller.protocol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lukaszkaleta
 * @since 25.04.13 12:51
 */
@Controller
@RequestMapping(value = "/protocol/accordance")
public class AccordanceController {

    @RequestMapping(value = "/show")
    public ModelAndView show() {
        ModelAndView modelAndView = new ModelAndView("protocol/accordance");
        return modelAndView;
    }
}
