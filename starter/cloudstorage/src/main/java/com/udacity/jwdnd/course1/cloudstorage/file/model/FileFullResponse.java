package com.udacity.jwdnd.course1.cloudstorage.file.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.InputStream;

public class FileFullResponse implements AutoCloseable {

    Logger logger = LoggerFactory.getLogger(FileFullResponse.class);

    private Integer fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private InputStream fileData;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public InputStream getFileData() {
        return fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }

    @Override
    public void close() {
        try {
            if (getFileData() != null) {
                getFileData().close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
