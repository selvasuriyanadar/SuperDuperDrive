package com.udacity.jwdnd.course1.cloudstorage.user.controller;

import com.udacity.jwdnd.course1.cloudstorage.user.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.user.model.UserForm;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
    public String signupUser(@ModelAttribute("user") UserForm form, Model model) {
        OpsResult result = insert(() -> userService.createUser(form), "There was an error signing you up. Please try again.");
        model.addAttribute("signupSuccess", result.getStatus() == OpsResult.Status.Success);
        model.addAttribute("signupError", result.getErrorMessage());
        return "signup";
    }

}
