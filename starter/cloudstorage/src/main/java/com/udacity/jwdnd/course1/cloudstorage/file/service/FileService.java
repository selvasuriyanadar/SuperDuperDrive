package com.udacity.jwdnd.course1.cloudstorage.file.service;

import com.udacity.jwdnd.course1.cloudstorage.file.model.File;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileFullResponse;
import com.udacity.jwdnd.course1.cloudstorage.file.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.file.logic.FileLogic;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int uploadFile(Integer userId, FileForm form) {
        try (final File file = form.getFile();) {
           FileLogic.validate(file, fileName -> doesFileExist(userId, fileName));
           file.setUserId(userId);
           return fileMapper.insert(file);
        }
    }

    public boolean doesFileExist(Integer userId, String fileName) {
        return fileMapper.getFileShortResponseByFileName(userId, fileName).isPresent();
    }

    public boolean doesFileExist(Integer userId, Integer fileId) {
        return fileMapper.getFileShortResponse(userId, fileId).isPresent();
    }

    public FileFullResponse fetchFileFullResponse(Integer userId, Integer fileId) {
        Optional<FileFullResponse> fileFullResponseOpt = fileMapper.getFileFullResponse(userId, fileId);
        if (!fileFullResponseOpt.isPresent()) {
            throw new InvalidStateException("Could not find file.");
        }
        return fileFullResponseOpt.get();
    }

    public Nothing delete(Integer userId, Integer fileId) {
        if (!doesFileExist(userId, fileId)) {
            throw new InvalidStateException("Could not find file.");
        }
        fileMapper.delete(userId, fileId);
        return new Nothing();
    }

}
