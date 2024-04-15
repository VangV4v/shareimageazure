package com.vang.shareimageazure.service;

import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface HandleExceptionService {

    ResponseEntity<String> accessDeniedHandle(Principal principal);
}