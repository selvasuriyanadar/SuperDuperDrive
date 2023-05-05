package com.udacity.jwdnd.course1.cloudstorage.note.service;

import com.udacity.jwdnd.course1.cloudstorage.note.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.note.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.note.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.note.logic.NoteLogic;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

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

    public Nothing editNote(Integer userId, Integer noteId, NoteForm form) {
        Optional<Note> note = noteMapper.getNote(userId, noteId);
        if (!note.isPresent()) {
            throw new InvalidStateException("Could not find the note.");
        }
        form.transfer(note.get());

        NoteLogic.validate(note.get());
        noteMapper.update(note.get());
        return new Nothing();
    }

    public boolean doesNoteExist(Integer userId, Integer noteId) {
        return noteMapper.getNote(userId, noteId).isPresent();
    }

    public Nothing delete(Integer userId, Integer noteId) {
        if (!doesNoteExist(userId, noteId)) {
            throw new InvalidStateException("Could not find note.");
        }
        noteMapper.delete(userId, noteId);
        return new Nothing();
    }

}
