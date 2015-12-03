package com.ra.dissection.protocol.mvc.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author lukaszkaleta
 * @since 25.04.13 12:04
 */
public class ActiveMenuInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }
        if (handler instanceof HandlerMethod) {
            RequestMapping requestMapping = ((HandlerMethod) handler).getBean().getClass().getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                String[] requestMappingValues = requestMapping.value();
                if (requestMappingValues != null) {
                    for (String requestMappingValue : requestMappingValues) {
                        String[] requestMappingValueParts = requestMappingValue.split("/");
                        String main = null;
                        String menu = null;
                        // First element is empty
                        // Second element may be main menu
                        // Third element may be sub menu
                        switch (requestMappingValueParts.length) {
                            case 3:
                                menu = requestMappingValueParts[2];
                            case 2:
                                main = requestMappingValueParts[1];
                                break;
                        }
                        if (main != null) {
                            modelAndView.addObject("mainMenu", main);
                            if (menu != null) {
                                modelAndView.addObject(main + "Menu", menu);
                            }
                        }
                    }
                }
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
