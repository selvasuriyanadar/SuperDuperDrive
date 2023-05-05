package com.udacity.jwdnd.course1.cloudstorage.file.mapper;

import com.udacity.jwdnd.course1.cloudstorage.file.model.File;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileShortResponse;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileFullResponse;

import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} and fileid = #{fileId}")
    Optional<FileShortResponse> getFileShortResponse(Integer userId, Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} and filename = #{fileName}")
    Optional<FileShortResponse> getFileShortResponseByFileName(Integer userId, String fileName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileShortResponse> getFileShortResponses(Integer userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} and fileid = #{fileId}")
    Optional<FileFullResponse> getFileFullResponse(Integer userId, Integer fileId);

    @Select("DELETE FROM FILES WHERE userid = #{userId} and fileid = #{fileId}")
    void delete(Integer userId, Integer fileId);

}
