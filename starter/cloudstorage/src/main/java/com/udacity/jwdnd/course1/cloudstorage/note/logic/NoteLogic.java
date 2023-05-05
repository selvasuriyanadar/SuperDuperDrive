package com.udacity.jwdnd.course1.cloudstorage.note.logic;

import com.udacity.jwdnd.course1.cloudstorage.note.model.Note;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import java.util.*;

public class NoteLogic {

    public static void validate(Note note) {
        validateNotNull(note.getNoteTitle(), "Note Title");
        validateNotNull(note.getNoteDescription(), "Note Description");
    }

}
