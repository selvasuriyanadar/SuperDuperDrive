package com.udacity.jwdnd.course1.cloudstorage.lib.spring.controller;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import static selva.oss.lang.Commons.*;
import static selva.oss.lang.operation.CurdOps.*;
import static selva.oss.lang.operation.ExceptionHandler.*;
import selva.oss.lang.operation.OpsResult;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class ResponseUtils {

    public static class StreamingResponse {
        public HttpHeaders httpHeaders;
        public StreamingResponseBody streamingResponseBody;

        public StreamingResponse(HttpHeaders httpHeaders, StreamingResponseBody streamingResponseBody) {
            validateNotNull(httpHeaders);
            validateNotNull(streamingResponseBody);

            this.httpHeaders = httpHeaders;
            this.streamingResponseBody = streamingResponseBody;
        }
    }

    public static ResponseEntity<StreamingResponseBody> toStreamingResponseEntity(Operation<StreamingResponse> operation, String errorMessage) {
        OpsResult<StreamingResponse> opsResult = toOpsResult(operation, errorMessage);
        if (opsResult.getStatus() == OpsResult.Status.Success) {
            return new ResponseEntity<StreamingResponseBody>(opsResult.getData().streamingResponseBody, opsResult.getData().httpHeaders, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<StreamingResponseBody>(out -> out.write(opsResult.getErrorMessage().getBytes(StandardCharsets.UTF_8)), HttpStatus.BAD_REQUEST);
        }
    }

    public static String transferTo(Model model, OpsResult opsResult, String resultPage) {
        return transferTo(model, opsResult, resultPage, resultPage);
    }

    public static String transferTo(Model model, OpsResult opsResult, String successPage, String errorPage) {
        if (opsResult.getStatus() == OpsResult.Status.Success) {
            model.addAttribute("success", true);
            return successPage;
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("error", opsResult.getErrorMessage());
            return errorPage;
        }
    }

    public static String transferToWithResponse(Model model, OpsResult opsResult, String responseKey, String successPage, String errorPage) {
        if (opsResult.getStatus() == OpsResult.Status.Success) {
            model.addAttribute("success", true);
            model.addAttribute(responseKey, opsResult.getData());
            return successPage;
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("error", opsResult.getErrorMessage());
            return errorPage;
        }
    }

}
