package com.vang.shareimageazure.controller;

import com.vang.shareimageazure.model.AuthRequestModel;
import com.vang.shareimageazure.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthRequestModel model) {

        return authService.authenticate(model);
    }
}