package com.udacity.jwdnd.course1.cloudstorage.note.model;

public class NoteForm {

    private String noteTitle;
    private String noteDescription;

    public Note getNote() {
        Note note = new Note();
        note.setNoteTitle(getNoteTitle());
        note.setNoteDescription(getNoteDescription());
        return note;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

}
