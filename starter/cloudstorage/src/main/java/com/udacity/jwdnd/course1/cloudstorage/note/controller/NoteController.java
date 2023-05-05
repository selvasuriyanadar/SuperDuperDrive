package com.udacity.jwdnd.course1.cloudstorage.note.controller;

import com.udacity.jwdnd.course1.cloudstorage.note.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.note.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.note.mapper.NoteMapper;
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
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteController(NoteService noteService, UserService userService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    @ModelAttribute("main")
    public String getMainPage() {
        return "note";
    }

    @ModelAttribute("noteTab")
    public boolean getTab() {
        return true;
    }

    @GetMapping()
    public String notes(Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> noteMapper.getNotes(userService.getUserId(authentication.getName())), "There was an error fetching the notes. Please try again.");
        return ResponseUtils.transferToWithResponse(model, result, "notes", "home", "result");
    }

    @PostMapping("/{noteId}")
    public String editNote(@ModelAttribute("note") NoteForm form, @PathVariable("noteId") Integer noteId, Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> noteService.editNote(userService.getUserId(authentication.getName()), noteId, form), "There was an error editing the note. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

    @PostMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> noteService.delete(userService.getUserId(authentication.getName()), noteId), "There was an error deleting the note. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

    @PostMapping()
    public String createNote(@ModelAttribute("note") NoteForm form, Model model, Authentication authentication) {
        OpsResult result = userService.insertForUser(authentication.getName(), (userId) -> noteService.createNote(userId, form), "There was an error creating the note. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

}
