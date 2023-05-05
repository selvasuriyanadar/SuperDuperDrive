package com.udacity.jwdnd.course1.cloudstorage.file.logic;

import com.udacity.jwdnd.course1.cloudstorage.file.model.File;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileForm;

import static selva.oss.lang.CommonValidations.*;
import static selva.oss.lang.Commons.*;

import java.util.*;

public class FileLogic {

    public interface DoesFileExist {
        public boolean exist(String fileName);
    }

    public static void validate(File file, DoesFileExist doesFileExist) {
        validateNotNull(file.getFileName(), "File Name");
        validateNotNull(file.getContentType(), "Content Type");
        validateNotNull(file.getFileSize(), "File Size");
        validateNotNull(file.getFileData(), "File Data");

        if (doesFileExist.exist(file.getFileName())) {
            throw new InvalidStateException("The File Name already exists.");
        }
    }

}
