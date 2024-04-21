package com.vang.shareimageazure.controller;

import com.vang.shareimageazure.model.GenerateCodeRequestModel;
import com.vang.shareimageazure.model.UpdatePasswordRequestModel;
import com.vang.shareimageazure.model.VerifyCodeModel;
import com.vang.shareimageazure.model.VerifyCodeResponseModel;
import com.vang.shareimageazure.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reset-password")
@CrossOrigin("*")
public class RestPasswordController {

    private final UserService userService;

    @Autowired
    public RestPasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/generate-code/")
    public ResponseEntity<String> resetPassword(@RequestBody GenerateCodeRequestModel model) {
        return userService.resetPassword(model.getEmail());
    }

    @PostMapping("/verify-code/")
    public ResponseEntity<VerifyCodeResponseModel> verifyCode(@RequestBody VerifyCodeModel model) {

        return userService.verifyCode(model);
    }

    @PostMapping("/update/")
    @Transactional
    public ResponseEntity<Boolean> updateNewPassword(@RequestBody UpdatePasswordRequestModel model) {
        return userService.updatePassword(model);
    }
}