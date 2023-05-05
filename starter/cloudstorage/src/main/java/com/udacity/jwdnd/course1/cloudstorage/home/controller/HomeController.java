package com.udacity.jwdnd.course1.cloudstorage.user.controller;

import com.udacity.jwdnd.course1.cloudstorage.user.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.file.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils;

import static selva.oss.lang.operation.CurdOps.*;
import static selva.oss.lang.operation.ExceptionHandler.*;
import selva.oss.lang.operation.OpsResult;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FileMapper fileMapper;

    public HomeController(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @ModelAttribute("main")
    public String getMainPage() {
        return "home";
    }

    @ModelAttribute("fileTab")
    public boolean getTab() {
        return true;
    }

    @GetMapping()
    public String home(Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> fileMapper.getFileShortResponses(userService.getUserId(authentication.getName())), "There was an error fetching the files. Please try again.");
        return ResponseUtils.transferToWithResponse(model, result, "files", "home", "result");
    }

}
