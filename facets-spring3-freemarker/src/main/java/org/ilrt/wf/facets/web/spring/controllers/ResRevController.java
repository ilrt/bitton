package org.ilrt.wf.facets.web.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResRevController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainView(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("contextPath", request.getContextPath());
        mav.setViewName("mainView");
        mav.addObject("message", "Hello World!");
        return mav;
    }
}