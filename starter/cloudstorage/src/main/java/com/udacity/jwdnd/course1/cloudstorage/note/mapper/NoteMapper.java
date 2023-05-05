package com.udacity.jwdnd.course1.cloudstorage.note.mapper;

import com.udacity.jwdnd.course1.cloudstorage.note.model.Note;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} and noteid = #{noteId}")
    Optional<Note> getNote(Integer userId, Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotes(Integer userId);

}

