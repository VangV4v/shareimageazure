package com.vang.shareimageazure.controller;

import com.vang.shareimageazure.service.HandleExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@ControllerAdvice
public class HandleErrorController {

    private final HandleExceptionService exceptionService;

    @Autowired
    public HandleErrorController(HandleExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> accessDenied(Principal principal) {

        return exceptionService.accessDeniedHandle(principal);
    }

}