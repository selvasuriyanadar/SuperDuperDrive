package com.udacity.jwdnd.course1.cloudstorage.note.controller;

import com.udacity.jwdnd.course1.cloudstorage.note.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.note.model.NoteForm;
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
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @ModelAttribute("main")
    public String getMainPage() {
        return "home";
    }

    @PostMapping()
    public String createNote(@ModelAttribute("note") NoteForm form, Model model, Authentication authentication) {
        OpsResult result = userService.insertForUser(authentication.getName(), (userId) -> noteService.createNote(userId, form), "There was an error creating the note. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

}
