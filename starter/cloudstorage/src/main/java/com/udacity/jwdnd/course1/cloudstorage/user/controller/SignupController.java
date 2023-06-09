package com.udacity.jwdnd.course1.cloudstorage.user.controller;

import com.udacity.jwdnd.course1.cloudstorage.user.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.user.model.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static selva.oss.lang.operation.CurdOps.*;
import selva.oss.lang.operation.OpsResult;

import java.util.*;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute("user") UserForm form, Model model, RedirectAttributes redirectAttributes) {
        OpsResult result = insert(() -> userService.createUser(form), "There was an error signing you up. Please try again.");
        redirectAttributes.addFlashAttribute("signup_success", true);
        return ResponseUtils.transferTo(model, result, "redirect:/login", "signup");
    }

}
