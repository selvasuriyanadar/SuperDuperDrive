package com.udacity.jwdnd.course1.cloudstorage.home.controller;

import static com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils.NotFoundException;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
public class CustomErrorController extends AbstractErrorController {

    private static final String ERROR_PATH=  "/error";

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * Just catching the {@linkplain NotFoundException} exceptions and render
     * the error.html error page.
     */
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFound(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("not_found", true);
        return modelAndView;
    }

    /**
     * Responsible for handling all errors and throw especial exceptions
     * for some HTTP status codes. Otherwise, it will return a map that
     * ultimately will be converted to a json error.
     */
    @RequestMapping(ERROR_PATH)
    public ModelAndView handleErrors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        if (status.equals(HttpStatus.NOT_FOUND) || status.equals(HttpStatus.METHOD_NOT_ALLOWED)) {
            throw new NotFoundException();
        }

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("unexpected", true);
        return modelAndView;
    }

}
