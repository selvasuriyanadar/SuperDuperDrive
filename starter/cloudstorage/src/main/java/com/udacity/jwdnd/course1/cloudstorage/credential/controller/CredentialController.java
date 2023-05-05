package com.udacity.jwdnd.course1.cloudstorage.credential.controller;

import com.udacity.jwdnd.course1.cloudstorage.credential.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.user.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static selva.oss.lang.operation.CurdOps.*;
import selva.oss.lang.operation.OpsResult;

import java.util.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @ModelAttribute("main")
    public String getMainPage() {
        return "home";
    }

    @PostMapping()
    public String createCredential(@ModelAttribute("credential") CredentialForm form, Model model, Authentication authentication) {
        OpsResult result = userService.insertForUser(authentication.getName(), (userId) -> credentialService.createCredential(userId, form), "There was an error creating the credential. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

}
