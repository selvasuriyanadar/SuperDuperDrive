package com.udacity.jwdnd.course1.cloudstorage.home.controller;

import static com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils.OperationNotAllowedException;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CustomAdviceController {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
      MaxUploadSizeExceededException exc,
      HttpServletRequest request,
      HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("not_allowed", true);
        return modelAndView;
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ModelAndView notAllowed(OperationNotAllowedException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("not_allowed", true);
        modelAndView.getModel().put("error_message", e.getMessage());
        return modelAndView;
    }

}
