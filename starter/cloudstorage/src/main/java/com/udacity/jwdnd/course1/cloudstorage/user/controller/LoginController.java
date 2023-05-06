package com.udacity.jwdnd.course1.cloudstorage.user.controller;

import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public ModelAndView loginView() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

}
