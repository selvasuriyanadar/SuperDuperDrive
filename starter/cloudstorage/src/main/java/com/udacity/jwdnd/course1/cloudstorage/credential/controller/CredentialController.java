package com.udacity.jwdnd.course1.cloudstorage.credential.controller;

import com.udacity.jwdnd.course1.cloudstorage.credential.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.credential.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.credential.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.user.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static selva.oss.lang.operation.CurdOps.*;
import static selva.oss.lang.operation.ExceptionHandler.*;
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
        return "credential";
    }

    @ModelAttribute("credentialTab")
    public boolean getTab() {
        return true;
    }

    @GetMapping()
    public String credentials(Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> credentialService.getCredentials(userService.getUserId(authentication.getName())), "There was an error fetching the credentials. Please try again.");
        return ResponseUtils.transferToWithResponse(model, result, "credentials", "home", "result");
    }

    @PostMapping("/{credentialId}")
    public String createCredential(@ModelAttribute("credential") CredentialForm form, @PathVariable("credentialId") Integer credentialId, Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> credentialService.editCredential(userService.getUserId(authentication.getName()), credentialId, form), "There was an error editing the credential. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

    @PostMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> credentialService.deleteCredential(userService.getUserId(authentication.getName()), credentialId), "There was an error deleting the credential. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

    @PostMapping()
    public String createCredential(@ModelAttribute("credential") CredentialForm form, Model model, Authentication authentication) {
        OpsResult result = userService.insertForUser(authentication.getName(), (userId) -> credentialService.createCredential(userId, form), "There was an error creating the credential. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

}
