package com.vang.shareimageazure.service;

import com.vang.shareimageazure.model.AuthRequestModel;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<String> authenticate(AuthRequestModel model);
}