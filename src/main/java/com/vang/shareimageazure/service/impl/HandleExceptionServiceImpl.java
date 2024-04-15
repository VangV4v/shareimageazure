package com.vang.shareimageazure.service.impl;

import com.vang.shareimageazure.service.HandleExceptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class HandleExceptionServiceImpl implements HandleExceptionService {

    @Override
    public ResponseEntity<String> accessDeniedHandle(Principal principal) {
        return new ResponseEntity<>(principal.getName()+" Cannot access", HttpStatus.FORBIDDEN);
    }
}
