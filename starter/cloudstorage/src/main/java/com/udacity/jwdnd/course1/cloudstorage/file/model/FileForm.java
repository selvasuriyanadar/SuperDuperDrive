package com.udacity.jwdnd.course1.cloudstorage.file.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileForm {

    private MultipartFile fileUpload;

    public static class FileSlippedOutException extends RuntimeException {
    }

    public File getFile() {
        if (fileUpload == null || fileUpload.isEmpty()) {
            return new File();
        }

        try {
            File file = new File();
            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(fileUpload.getContentType());
            file.setFileSize(fileUpload.getSize());
            file.setFileData(fileUpload.getInputStream());
            return file;
        }
        catch (IOException e) {
            throw new FileSlippedOutException();
        }
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

}
