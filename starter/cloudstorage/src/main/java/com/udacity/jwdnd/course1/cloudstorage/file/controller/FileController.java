package com.udacity.jwdnd.course1.cloudstorage.file.controller;

import com.udacity.jwdnd.course1.cloudstorage.file.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.file.model.FileFullResponse;
import com.udacity.jwdnd.course1.cloudstorage.user.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller.ResponseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import static selva.oss.lang.operation.CurdOps.*;
import static selva.oss.lang.operation.ExceptionHandler.*;
import selva.oss.lang.operation.OpsResult;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @ModelAttribute("main")
    public String getMainPage() {
        return "home";
    }

    @PostMapping()
    public String uploadFile(@ModelAttribute("file") FileForm form, Model model, Authentication authentication) {
        OpsResult result = userService.insertForUser(authentication.getName(), (userId) -> fileService.uploadFile(userId, form), "There was an error uploading the file. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

    @PostMapping("/{fileId}")
    public String postFile(@PathVariable("fileId") Integer fileId, Model model, Authentication authentication) {
        OpsResult result = toOpsResult(() -> fileService.delete(userService.getUserId(authentication.getName()), fileId), "There was an error deleting the file. Please try again.");
        return ResponseUtils.transferTo(model, result, "result");
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<StreamingResponseBody> getFile(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        return ResponseUtils.toStreamingResponseEntity(() -> {
            try (FileFullResponse fileFullResponse = fileService.fetchFileFullResponse(userService.getUserId(authentication.getName()), fileId)) {
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Content-Disposition",
                        "attachment;filename=" + fileFullResponse.getFileName());
                responseHeaders.set("Content-Type",
                        fileFullResponse.getContentType());
                return new ResponseUtils.StreamingResponse(responseHeaders, out -> {
                    fileFullResponse.getFileData().transferTo(out);
                });
            }
        }, "There was an error fetching the file. Please try again.");
    }

}
