package com.vang.shareimageazure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test-1")
public class TestController {

    @GetMapping
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Test success", HttpStatus.OK);
    }
}
