package com.udacity.jwdnd.course1.cloudstorage.note.service;

import com.udacity.jwdnd.course1.cloudstorage.note.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.note.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.note.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.note.logic.NoteLogic;

import static selva.oss.lang.CommonValidations.*;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Integer userId, NoteForm form) {
        final Note note = form.getNote();

        NoteLogic.validate(note);
        note.setUserId(userId);
        return noteMapper.insert(note);
    }

}
